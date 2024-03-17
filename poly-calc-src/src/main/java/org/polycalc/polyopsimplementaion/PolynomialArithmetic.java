package org.polycalc.polyopsimplementaion;

import org.polycalc.model.Monomial;
import org.polycalc.model.Polynomial;
import org.polycalc.operations.Arithmetic;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PolynomialArithmetic implements Arithmetic {

    @Override
    public Polynomial add(Polynomial p1, Polynomial p2) {

        Iterator<Map.Entry<Integer, Monomial>> itr1 = p1.getTerms().entrySet().iterator();
        Iterator<Map.Entry<Integer, Monomial>> itr2 = p2.getTerms().entrySet().iterator();
        Polynomial result = new Polynomial();

        if (p1.getTerms().isEmpty() && !p2.getTerms().isEmpty()) {
            return p2;
        } else if (p2.getTerms().isEmpty() && !p1.getTerms().isEmpty()) {
            return p1;
        }
        Monomial monomial1 = itr1.hasNext() ? itr1.next().getValue() : null;
        Monomial monomial2 = itr2.hasNext() ? itr2.next().getValue() : null;

        while (monomial1 != null && monomial2 != null) {
            if (monomial1.getExpo() > monomial2.getExpo()) {
                result.getTerms().put(monomial1.getExpo(), monomial1);
                monomial1 = itr1.hasNext() ? itr1.next().getValue() : null;
            } else if (monomial1.getExpo() < monomial2.getExpo()) {
                result.getTerms().put(monomial2.getExpo(), monomial2);
                monomial2 = itr2.hasNext() ? itr2.next().getValue() : null;
            } else {
                Monomial monomial = new Monomial();
                monomial.setCoeff(monomial1.getCoeff() + monomial2.getCoeff());
                monomial.setExpo(monomial1.getExpo());
                monomial.setVar(monomial1.getVar());
                result.getTerms().put(monomial.getExpo(), monomial);
                monomial1 = itr1.hasNext() ? itr1.next().getValue() : null;
                monomial2 = itr2.hasNext() ? itr2.next().getValue() : null;
            }
        }
        while (monomial1 != null) {
            result.getTerms().put(monomial1.getExpo(), monomial1);
            monomial1 = itr1.hasNext() ? itr1.next().getValue() : null;
        }
        while (monomial2 != null) {
            result.getTerms().put(monomial2.getExpo(), monomial2);
            monomial2 = itr2.hasNext() ? itr2.next().getValue() : null;
        }
        return result;
    }

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
        return result;
    }

    @Override
    public Polynomial multiply(Polynomial p1, Polynomial p2) {

        Iterator<Map.Entry<Integer, Monomial>> iterator1 = p1.getTerms().entrySet().iterator();
        Polynomial result = new Polynomial();
        while(iterator1.hasNext()){
            Polynomial interPoly = new Polynomial();
            Monomial m1 = iterator1.next().getValue();
            Iterator<Map.Entry<Integer, Monomial>> iterator2 = p2.getTerms().entrySet().iterator();
            while (iterator2.hasNext()) {
                Monomial m2 = iterator2.next().getValue();
                Monomial m3 = multiplyMonomial(m1, m2);
                interPoly.getTerms().put(m3.getExpo(),m3);
            }
            result = add(result, interPoly);
        }
        return result;
    }

    @Override
    public HashMap<String, Polynomial> divide(Polynomial p1, Polynomial p2) {

        Polynomial result = new Polynomial();
        HashMap<String, Polynomial> finalResult = new HashMap<>();

        int higherDegreeP1 = p1.getHigherDegree();
        int higherDegreeP2 = p2.getHigherDegree();

        if (higherDegreeP2 > higherDegreeP1)
            return finalResult;

        double coeffP2 = p2.getTerms().get(higherDegreeP2).getCoeff();
        if (coeffP2 > 1) {
            p2 = divideByScalar(p2, coeffP2);
        }

        Polynomial reminder = p1;
        Polynomial divisor = p2;

        int higherDegreeOfReminder = reminder.getHigherDegree();
        while (higherDegreeOfReminder >= p2.getHigherDegree()) {
            Monomial monomial = new Monomial(reminder.getTerms().get(higherDegreeOfReminder).getCoeff(),
                    higherDegreeOfReminder - divisor.getHigherDegree());

            result.getTerms().put(monomial.getExpo(), monomial);

            Polynomial polynomialToMultiply = new Polynomial();
            polynomialToMultiply.getTerms().put(monomial.getExpo(), monomial);

            divisor = multiply(divisor, polynomialToMultiply);
            divisor = multiplyWithScalar(divisor, -1);

            reminder = add(reminder, divisor);
            Iterator<Map.Entry<Integer, Monomial>> iterator = reminder.getTerms().entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<Integer, Monomial> entry = iterator.next();
                if (entry.getValue().getCoeff() == 0) {
                    iterator.remove();
                }
            }
            higherDegreeOfReminder = reminder.getHigherDegree();
            divisor = p2;
        }

        result = divideByScalar(result, coeffP2);
        p2 = multiplyWithScalar(p2, coeffP2);

        finalResult.put("result", result);
        finalResult.put("reminder", reminder);
        finalResult.put("divisor", p2);

        return finalResult;
    }

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
        return p;
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
        Iterator<Map.Entry<Integer, Monomial>> iterator = polynomial.getTerms().entrySet().iterator();
        while (iterator.hasNext()) {
            Monomial mo = iterator.next().getValue();
            iterator.next().getValue().setCoeff(mo.getCoeff() / value);
        }
        return polynomial;
    }
}
