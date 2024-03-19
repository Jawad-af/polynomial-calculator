package org.polycalc.operations.service;

import org.polycalc.model.Monomial;
import org.polycalc.model.Polynomial;
import org.polycalc.operations.api.Arithmetic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PolynomialArithmetic implements Arithmetic {

    // ------------------------------------------------------------------------------------------------------------------------ ADDITION
    public Polynomial add(Polynomial p1, Polynomial p2) {
        Polynomial result = new Polynomial();
        if (isEmpty(p1) && !isEmpty(p2)) {
            return p2;
        } else if (isEmpty(p2) && !isEmpty(p1)) {
            return p1;
        }
        addPolynomials(p1, p2, result);
        removeZeroCoefficients(result);
        return result;
    }

    private void addPolynomials(Polynomial p1, Polynomial p2, Polynomial result) {
        Iterator<Map.Entry<Integer, Monomial>> itr1 = p1.getTerms().entrySet().iterator();
        Iterator<Map.Entry<Integer, Monomial>> itr2 = p2.getTerms().entrySet().iterator();

        Monomial monomial1 = getNextMonomial(itr1);
        Monomial monomial2 = getNextMonomial(itr2);
        // The algorithm iterates over both the elements of both polynomials
        // and adds the coefficients of the monomials that have the same exponential
        // it stops when one of the iterators reaches the end of the polynomial its iterating on
        while (monomial1 != null && monomial2 != null) {
            if (monomial1.getExpo() > monomial2.getExpo()) {
                result.getTerms().put(monomial1.getExpo(), monomial1);
                monomial1 = getNextMonomial(itr1);
            } else if (monomial1.getExpo() < monomial2.getExpo()) {
                result.getTerms().put(monomial2.getExpo(), monomial2);
                monomial2 = getNextMonomial(itr2);
            } else {
                // if equal add monomials (merge them)
                Monomial mergedMonomial = mergeMonomials(monomial1, monomial2);
                result.getTerms().put(mergedMonomial.getExpo(), mergedMonomial);
                monomial1 = getNextMonomial(itr1);
                monomial2 = getNextMonomial(itr2);
            }
        }
        addRemainingTerms(itr1, monomial1, result);
        addRemainingTerms(itr2, monomial2, result);
    }

    // ------------------------------------------------------------------------------------------------------------------------ SUBTRACTION
    @Override
    public Polynomial subtract(Polynomial p1, Polynomial p2) {
        Polynomial result = new Polynomial();
        // Create a copy of p2
        Polynomial temp = new Polynomial();
        for (Map.Entry<Integer, Monomial> entry : p2.getTerms().entrySet()) {
            int exponent = entry.getKey();
            Monomial monomial = entry.getValue();
            temp.getTerms().put(exponent, new Monomial(monomial.getCoeff(), exponent));
        }
        // Multiply each term of the copied p2 by -1
        temp = multiplyWithScalar(temp, -1);
        // Add p1 and the modified p2
        result = add(p1, temp);
        removeZeroCoefficients(result);
        return result;
    }

    // ------------------------------------------------------------------------------------------------------------------------ MULTIPLICATION
    @Override
    public Polynomial multiply(Polynomial p1, Polynomial p2) {
        Polynomial result = new Polynomial();
        Iterator<Map.Entry<Integer, Monomial>> iterator1 = p1.getTerms().entrySet().iterator();
        while(iterator1.hasNext()){
            // This polynomial is used for intermediate results
            Polynomial interPoly = new Polynomial();
            // Store the first term from the first polynomial
            Monomial m1 = iterator1.next().getValue();
            Iterator<Map.Entry<Integer, Monomial>> iterator2 = p2.getTerms().entrySet().iterator();
            // Iterate over all elements of the second polynomial, multiply polynomials
            while (iterator2.hasNext()) {
                Monomial m2 = iterator2.next().getValue();
                Monomial m3 = multiplyMonomial(m1, m2);
                interPoly.getTerms().put(m3.getExpo(),m3);
            }
            // Add intermediate results of the multiplication
            result = add(result, interPoly);
        }
        removeZeroCoefficients(result);
        return result;
    }

    // ------------------------------------------------------------------------------------------------------------------------ DIVISION
    @Override
    public HashMap<String, Polynomial> divide(Polynomial p1, Polynomial p2) {

        Polynomial result = new Polynomial();
        // The hashmap is used to store the final result as it might consist of multiple polynomials
        HashMap<String, Polynomial> finalResult = new HashMap<>();

        int higherDegreeP1 = p1.getHigherDegree();
        int higherDegreeP2 = p2.getHigherDegree();
        // Checking whether p2 is simply a constant
        if(p2.getHigherDegree() == 0){
            result = divideByScalar(p1, p2.getTerms().get(p2.getHigherDegree()).getCoeff());
            finalResult.put("result", result);
            return finalResult;
        }
        // Store the coefficient of p2 for further use
        double coefficientOfP2 = p2.getTerms().get(higherDegreeP2).getCoeff();
        // Normalize p2 if its highest degree term's coefficient is non-zero
        if (coefficientOfP2 != 0) {
            p2 = divideByScalar(p2, coefficientOfP2);
        }
        // Initialize temporary variables for division algorithm
        Polynomial reminder = p1;
        Polynomial divisor = p2;

        int higherDegreeOfReminder = reminder.getHigherDegree();
        // Continue division until the degree of reminder is less than divisor
        while (higherDegreeOfReminder >= p2.getHigherDegree()) {
            // Create monomial for the current division step, that will be used to
            // to be multiplied with the divisor (this monomial represent the first term
            // of the result of the division algorithm)
            Monomial monomial = createMonomial(reminder, higherDegreeOfReminder, divisor);
            // Store the first term of the final result
            result.getTerms().put(monomial.getExpo(), monomial);

            Polynomial polynomialToMultiply = new Polynomial();
            polynomialToMultiply.getTerms().put(monomial.getExpo(), monomial);
            // This method will multiply the divisor with the first term of the final result
            // and it will multiply the divisor with (-1) in order to subtract it
            // from the numerator (which is represented at this stage as a reminder)
            divisor = updateDivisor(divisor, polynomialToMultiply);
            reminder = add(reminder, divisor);
            // Remove the terms with zero coefficients resulted of the subtraction operation
            removeZeroCoefficients(reminder);
            // Check for the updated highest degree of the reminder (numerator)
            higherDegreeOfReminder = reminder.getHigherDegree();
            // Update the value of the divisor
            divisor = p2;
        }
        // This part of the code is useful if the divisor has a coefficient
        // we did normalize the divisor at early stages of the division algorithm
        result = divideByScalar(result, coefficientOfP2);
        p2 = multiplyWithScalar(p2, coefficientOfP2);

        finalResult.put("result", result);
        finalResult.put("reminder", reminder);
        finalResult.put("divisor", p2);

        return finalResult;
    }

    // ------------------------------------------------------------------------------------------------------------------------ INTEGRATION
    @Override
    public Polynomial integrate(Polynomial p) {
        if (p.getTerms().isEmpty()) {
            return p;
        }
        for (Monomial monomial : p.getTerms().values()) {
            int expo = monomial.getExpo() + 1;
            double coeff = monomial.getCoeff() / expo;
            monomial.setExpo(expo);
            monomial.setCoeff(coeff);
        }
        return p;
    }

    // ------------------------------------------------------------------------------------------------------------------------ DIFFERENTIATION
    @Override
    public Polynomial differentiate(Polynomial p) {

        if (p.getTerms().isEmpty()) {
            return p;
        }
        // ConcurrentHashMap is used to avoid the "ConcurrentModificationException"
        ConcurrentHashMap<Integer, Monomial> concurrentHashMap = new ConcurrentHashMap();
        concurrentHashMap.putAll(p.getTerms());

        Iterator<Map.Entry<Integer, Monomial>> iterator = concurrentHashMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Monomial> entry = iterator.next();
            int expo = entry.getValue().getExpo();
            double coeff = entry.getValue().getCoeff();
            iterator.remove();
            if (expo > 0) {
                concurrentHashMap.put(expo - 1, new Monomial(expo * coeff, expo - 1));
            }
        }
        p.getTerms().clear();
        p.getTerms().putAll(concurrentHashMap);
        removeZeroCoefficients(p);
        return p;
    }

    // ------------------------------------------------------------------------------------------------------------------------ HELPER METHODS
    private boolean isEmpty(Polynomial polynomial) {
        return polynomial.getTerms().isEmpty();
    }

    private Monomial getNextMonomial(Iterator<Map.Entry<Integer, Monomial>> iterator) {
        return iterator.hasNext() ? iterator.next().getValue() : null;
    }

    private Monomial mergeMonomials(Monomial monomial1, Monomial monomial2) {
        Monomial mergedMonomial = new Monomial();
        mergedMonomial.setCoeff(monomial1.getCoeff() + monomial2.getCoeff());
        mergedMonomial.setExpo(monomial1.getExpo());
        mergedMonomial.setVar(monomial1.getVar());
        return mergedMonomial;
    }

    private void addRemainingTerms(Iterator<Map.Entry<Integer, Monomial>> iterator, Monomial monomial, Polynomial result) {
        while (monomial != null) {
            result.getTerms().put(monomial.getExpo(), monomial);
            monomial = iterator.hasNext() ? iterator.next().getValue() : null;
        }
    }

    private static void removeZeroCoefficients(Polynomial reminder) {
        Iterator<Map.Entry<Integer, Monomial>> iterator = reminder.getTerms().entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<Integer, Monomial> entry = iterator.next();
            if (entry.getValue().getCoeff() == 0) {
                iterator.remove();
            }
        }
    }

    private Polynomial updateDivisor(Polynomial divisor, Polynomial polynomialToMultiply) {
        divisor = multiply(divisor, polynomialToMultiply);
        return multiplyWithScalar(divisor, -1);
    }

    private static Monomial createMonomial(Polynomial reminder, int higherDegreeOfReminder, Polynomial divisor) {
        return new Monomial(reminder.getTerms().get(higherDegreeOfReminder).getCoeff(),
                higherDegreeOfReminder - divisor.getHigherDegree());
    }

    private Polynomial multiplyWithScalar(Polynomial polynomial, double value) {
        Iterator<Map.Entry<Integer, Monomial>> iterator = polynomial.getTerms().entrySet().iterator();
        while (iterator.hasNext()) {
            Monomial mo = iterator.next().getValue();
            mo.setCoeff(mo.getCoeff() * value);
            polynomial.getTerms().replace(mo.getExpo(), mo);
        }
        return polynomial;
    }

    private Monomial multiplyMonomial(Monomial m1, Monomial m2) {
        Monomial m3 = new Monomial();
        m3.setCoeff(m1.getCoeff() * m2.getCoeff());
        m3.setExpo(m1.getExpo() + m2.getExpo());
        return m3;
    }

    private Polynomial divideByScalar(Polynomial polynomial, double value) {
        try {
            if (value == 0) {
                throw new ArithmeticException("Error: Division by zero");
            }
            Iterator<Map.Entry<Integer, Monomial>> iterator = polynomial.getTerms().entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Integer, Monomial> entry = iterator.next();
                double coeff = entry.getValue().getCoeff();
                entry.getValue().setCoeff(coeff / value);
            }
        } catch (ArithmeticException e) {
            System.err.println("Error: Division by zero");
        }
        return polynomial;
    }
}
