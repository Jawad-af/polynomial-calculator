package org.polycalc.service;

import org.polycalc.model.Polynomial;

public interface PolyOperations {

    Polynomial add(Polynomial p1, Polynomial p2);
    Polynomial subtract(Polynomial p1, Polynomial p2);
    Polynomial multiply(Polynomial p1, Polynomial p2);
    Polynomial divide(Polynomial p1, Polynomial p2);
    Polynomial integrate(Polynomial p);
    Polynomial derive(Polynomial p);

}
