package org.polycalc.model;

import org.polycalc.globals.Variable;
import org.polycalc.util.SuperScript;

import java.util.*;

public class Polynomial {

    private static NavigableMap<Integer, Monomial> terms = new TreeMap<>(Collections.reverseOrder());

    public int getHigherDegree(){
        return terms.firstEntry().getKey();
    }

    public NavigableMap<Integer, Monomial> getTerms() {
        return terms;
    }

    public Variable getVariable() {
        return Monomial.var;
    }

    public void toString(Polynomial polynomial) {

        Iterator<Map.Entry<Integer, Monomial>> iterator = polynomial.getTerms().entrySet().iterator();
        Variable variable = polynomial.getVariable();

        while (iterator.hasNext()) {
            Map.Entry entry = iterator.next();
            Monomial monomial = (Monomial) entry.getValue();
            double coeff = monomial.getCoeff();
            int expo = monomial.getExpo();

            if (coeff == 0) {
                continue;
            } else if (coeff == 1 && expo != 0) {
                System.out.print(variable + SuperScript.convert(expo));
            } else if (expo == 0) {
                System.out.print(coeff);
            } else if (expo == 1) {
                System.out.print( coeff + variable.getName());
            } else {
                System.out.print( coeff + variable.getName() + SuperScript.convert(expo));
            }
            if (iterator.hasNext()) {
                System.out.print(" + ");
            }
        }
    }
}






