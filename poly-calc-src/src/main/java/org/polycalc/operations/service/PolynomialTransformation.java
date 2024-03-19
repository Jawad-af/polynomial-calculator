package org.polycalc.operations.service;

import org.polycalc.globals.Variable;
import org.polycalc.model.Monomial;
import org.polycalc.model.Polynomial;
import org.polycalc.operations.api.Transformation;
import org.polycalc.util.SuperScript;

import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PolynomialTransformation implements Transformation {

//      The pattern was tested on this input
//      "-323x+ 12.1x^ + dfx^4 + 2x  ^8 - x + 1 +5 -0 +43.1x^  9 -23.5x + 32c -   3x "
    @Override
    public Polynomial parse(String input) {

        input = input.replaceAll("\\s", "");
        String polynomialIdentifier = "([-+]?\\s*\\d*\\.?\\d*X\\s*\\^*\\s*[1-9]*)|(\\b[1-9]+\\b)|([+-]\\s*[0-9]+)";
        Pattern monomialParser = Pattern.compile(polynomialIdentifier, Pattern.CASE_INSENSITIVE);
        Matcher monomial = monomialParser.matcher(input);

        Polynomial polynomial = new Polynomial();
        Variable variable = Variable.getInstance();

        while (monomial.find()) {

            Double coefficient = 1.0;
            int exponent = 1;

            String match = monomial.group();
            if (match.contains("x")) {
                String noSpaceMatch = match.replaceAll("\\s", "");
                String[] splitMatch = noSpaceMatch.split("x");

                if (splitMatch.length > 0) {
                    // check whether it contains a number
                    if (!splitMatch[0].isEmpty() && splitMatch[0].matches("\\-")) {
                        coefficient = -1.0;
                        // handle the case that it has only a positive sign
                    } else if (splitMatch[0].matches("[+-]?(?:\\d*\\.?\\d+)")) {
                        coefficient = Double.parseDouble(splitMatch[0]);
                    }
                }

                if (splitMatch.length > 1) {
                    // get rid of the ^ sign
                    String isolateExpo = splitMatch[1].replaceAll("\\^", "");
                    // check whether it contains a number
                    if (isolateExpo.matches("[2-9]+")) {
                        exponent = Integer.parseInt(isolateExpo);
                    }
                }

                if (polynomial.getTerms().containsKey(exponent)) {
                    double currentCoeff = polynomial.getTerms().get(exponent).getCoeff();
                    polynomial.getTerms().get(exponent).setCoeff(currentCoeff + coefficient);
                } else {
                    polynomial.getTerms().put(exponent, new Monomial(coefficient, variable, exponent));
                }
            } else {
                String noSpaceMatch = match.replaceAll("\\s", "");
                Monomial noVarMonomial = new Monomial(Double.parseDouble(noSpaceMatch), variable, 0);
                if (polynomial.getTerms().containsKey(0)) {
                    polynomial.getTerms().put(0, noVarMonomial);
                } else {
                    polynomial.getTerms().put(0, noVarMonomial);
                }
            }
        }
        return polynomial;
    }

    @Override
    public String convertToString(Polynomial polynomial) {
        if (polynomial == null || polynomial.getTerms().isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        Iterator<Map.Entry<Integer, Monomial>> iterator = polynomial.getTerms().entrySet().iterator();
        Variable variable = polynomial.getVariable();
        Monomial monomial = iterator.hasNext() ? iterator.next().getValue() : null;

        while (monomial != null) {

            double coeff = monomial.getCoeff();
            int expo = monomial.getExpo();

            if (coeff == 0) {
                monomial = iterator.hasNext() ? iterator.next().getValue() : null;
                continue;
            } else if (coeff == 1 && expo == 1) {
                result.append(variable.getName());
            } else if (coeff == 1.0 && expo != 0) {
                result.append(variable.getName() + SuperScript.convert(expo));
            }else if (expo == 0) {
                result.append(coeff);
            } else if (expo == 1) {
                result.append(coeff + variable.getName());
            } else {
                result.append(coeff + variable.getName() + SuperScript.convert(expo));
            }
            monomial = iterator.hasNext() ? iterator.next().getValue() : null;

            if (monomial != null && monomial.getCoeff() > 0) {
                result.append(" +");
            } else if (monomial != null && monomial.getCoeff() < 0){
                result.append(" ");
            }
        }
        return result.toString();
    }
}
