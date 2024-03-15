package org.polycalc.util;

import org.polycalc.globals.Variable;
import org.polycalc.model.Monomial;
import org.polycalc.model.Polynomial;

import java.util.Iterator;
import java.util.Map;

public class ToString {
    public String convert(Polynomial polynomial) {

        StringBuilder result = new StringBuilder();
        Iterator<Map.Entry<Integer, Monomial>> iterator = polynomial.getTerms().entrySet().iterator();
        Variable variable = polynomial.getVariable();
        Monomial monomial = iterator.hasNext() ? iterator.next().getValue() : null;

        while (monomial != null) {

            double coeff = monomial.getCoeff();
            int expo = monomial.getExpo();

            if (coeff == 0) {
                continue;
            } else if (coeff == 1 && expo == 1) {
                result.append(variable.getName());
            } else if (coeff == 1 && expo != 0) {
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
