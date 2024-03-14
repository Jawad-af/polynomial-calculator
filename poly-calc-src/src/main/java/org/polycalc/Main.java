package org.polycalc;

import org.polycalc.globals.Variable;
import org.polycalc.model.Monomial;
import org.polycalc.model.Polynomial;
import org.polycalc.util.PolyParse;

public class Main {
    public static void main(String[] args) {

        // testing global variables
        Monomial monomial = new Monomial();
        Variable var3 = Variable.getInstance();
        System.out.println("var3" + var3.getName());
        Variable variable2 = Variable.getInstance();
        monomial = new Monomial(2, variable2, 6);

        System.out.println("Var" + variable2.getName());
        variable2.setName("y");
        System.out.println(monomial.getVar());
        System.out.println(monomial.getCoeff());
        System.out.println(monomial.getExpo());

        Variable variable1 = Variable.getInstance();
        System.out.println(variable1.getName());
        System.out.println("var3" + var3.getName());

    }
}






















