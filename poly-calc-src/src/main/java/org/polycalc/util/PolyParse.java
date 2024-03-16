package org.polycalc.util;

import org.polycalc.globals.Variable;
import org.polycalc.model.Monomial;
import org.polycalc.model.Polynomial;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PolyParse {

    public Polynomial extractPoly(String input) {
//      The pattern was tested on this input
//      Matcher matcher = pattern.matcher("-323x+ 12.1x^ + dfx^4 + 2x  ^8 - x + 1 +5 -0 +43.1x^  9 -23.5x + 32c -   3x ");
        String regExPattern = "([-+]?\\s*\\d*\\.?\\d*X\\s*\\^*\\s*[1-9]*)|(\\b[1-9]+\\b)|([+-]\\s*[0-9]+)";
        Pattern pattern = Pattern.compile(regExPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);

        Polynomial polynomial = new Polynomial();
        Variable variable = polynomial.getVariable();

        while (matcher.find()) {

            Double coefficient = 1.0;
            int exponent = 1;

            String match = matcher.group();
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
                        // check whether it contains only negative sign
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
}
