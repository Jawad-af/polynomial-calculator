package org.polycalc;

import org.polycalc.model.Monomial;
import org.polycalc.model.Polynomial;
import org.polycalc.service.PolyOperations;
import org.polycalc.service.PolyOperationsImplementation;
import org.polycalc.util.SuperScript;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
//        Polynomial polynomial = new Polynomial();
//        Polynomial polynomial2 = new Polynomial();
//        SuperScript superScript = new SuperScript();
//
//        polynomial.getTerms().put(0, new Monomial(7, 'X', 0));
//        polynomial.getTerms().put(1, new Monomial(2, 'X', 1));
//        polynomial.getTerms().put(3, new Monomial(1, 'X', 3));
//        polynomial.getTerms().put(5, new Monomial(6, 'X', 5));
//
//        polynomial2.getTerms().put(0, new Monomial(2, 'X', 0));
//        polynomial2.getTerms().put(2, new Monomial(1, 'X', 2));
//        polynomial2.getTerms().put(4, new Monomial(5, 'X', 4));
//        polynomial2.getTerms().put(5, new Monomial(1, 'X', 5));
//
//        PolyOperations operations = new PolyOperationsImplementation();
//        Polynomial polynomial3 = operations.add(polynomial, polynomial2);
//
//
//
//        Iterator<Map.Entry<Double, Monomial>> iterator = polynomial3.getTerms().entrySet().iterator();
//        while (iterator.hasNext()) {
//            Map.Entry entry = iterator.next();
//            Monomial monomial = (Monomial) entry.getValue();
//            if (monomial.getCoeff() == 0) {
//                continue;
//            } else if (monomial.getCoeff() == 1 && monomial.getExpo() != 0) {
//                System.out.print(monomial.getVar() + SuperScript.convert(monomial.getExpo()));
//            } else if (monomial.getExpo() == 0) {
//                System.out.print((int) monomial.getCoeff());
//            } else if (monomial.getExpo() == 1) {
//                System.out.print((int) monomial.getCoeff() + monomial.getVar());
//            } else {
//                System.out.print((int) monomial.getCoeff() + monomial.getVar() + SuperScript.convert(monomial.getExpo()));
//            }
//            if (iterator.hasNext()) {
//                System.out.print(" + ");
//            }
//        }
        Character variable = 'x';
        Pattern pattern = Pattern.compile("([-+]?\\s*\\d*\\.?\\d*X\\s*\\^*\\s*[1-9]*)|(\\b[1-9]+\\b)", Pattern.CASE_INSENSITIVE);

        Matcher matcher = pattern.matcher("-323x+ 12.1x^ + dfx^4 + 2x  ^8 - x + 1 +5 -0 +43.1x^  9 -23.5x + 32c -   3x ");

        Polynomial polynomial = new Polynomial();

        while (matcher.find()) {

            Double coefficient = 1.0;
            int exponent = 1;

            String match = matcher.group();
            System.out.println("Match found: " + match);

            if (match.contains("x")) {
                String noSpaceMatch = match.replaceAll("\\s", "");
                System.out.println("without spaces: " + noSpaceMatch);
                
                String[] splitMatch = noSpaceMatch.split("x");

                // check whether it contains a number
                if (splitMatch[0].matches("[+-]?(?:\\d*\\.?\\d+)")) {
                    coefficient = Double.parseDouble(splitMatch[0]);
                    System.out.println("Coeff = " + coefficient);
                    // check whether it contains only negative sign
                } else if (splitMatch[0].matches("-+")) {
                    coefficient = -1.0;
                    System.out.println("Coeff = " + coefficient);
                    // handle the case that it has only a positive sign
                }

                if (splitMatch.length > 1) {
                    // get rid of the ^ sign
                    String isolateExpo = splitMatch[1].replaceAll("\\^", "");
                    System.out.println("Exponent = " + isolateExpo);
                    // check whether it contains a number
                    if (isolateExpo.matches("[2-9]")) {
                        exponent = Integer.parseInt(isolateExpo);
                        System.out.println("expo = " + exponent);
                    }
                }

                if(polynomial.getTerms().containsKey(exponent)){
                    double currentCoeff = polynomial.getTerms().get(exponent).getCoeff();
                    polynomial.getTerms().get(exponent).setCoeff(currentCoeff + coefficient);
                } else{
                    polynomial.getTerms().put(exponent, new Monomial(coefficient, variable, exponent));
                }
            } else {
                Monomial noVarMonomial = new Monomial(Double.parseDouble(match), variable, 0);
                if(polynomial.getTerms().containsKey(0)){
                    polynomial.getTerms().put(0, noVarMonomial);
                }else{
                    polynomial.getTerms().put(0, noVarMonomial);
                }
            }
            System.out.println();
        }

        Iterator<Map.Entry<Integer, Monomial>> iterator = polynomial.getTerms().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = iterator.next();
            Monomial monomial = (Monomial) entry.getValue();
            double coeff = monomial.getCoeff();
            int expo = monomial.getExpo();

            if (coeff % 1 == 0) {
                coeff = (int) coeff;
            }

            if (coeff == 0) {
                continue;
            } else if (coeff == 1 && expo != 0) {
                System.out.print(variable + SuperScript.convert(expo));
            } else if (expo == 0) {
                System.out.print(coeff);
            } else if (expo == 1) {
                System.out.print( coeff + variable.toString());
            } else {
                System.out.print( coeff + variable.toString() + SuperScript.convert(expo));
            }
            if (iterator.hasNext()) {
                System.out.print(" + ");
            }
        }
    }
}






















