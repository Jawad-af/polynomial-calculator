package org.polycalc;

import org.polycalc.globals.Variable;
import org.polycalc.model.Monomial;
import org.polycalc.model.Polynomial;
import org.polycalc.service.PolyOperations;
import org.polycalc.service.PolyOperationsImplementation;
import org.polycalc.util.PolyParse;

public class Main {
    public static void main(String[] args) {

        PolyOperations polyOperations = new PolyOperationsImplementation();
        PolyParse parse = new PolyParse();

        Polynomial p1 = parse.extractPoly("3x^5 + x^3 +x +5");
        Polynomial p2 = parse.extractPoly("x^4+2x^2-3x");
        Polynomial p3 = polyOperations.add(p1, p2);
        Polynomial p4 = polyOperations.subtract(p1, p2);

        p3.print();
        System.out.println();
        p4.print();


    }
}






















