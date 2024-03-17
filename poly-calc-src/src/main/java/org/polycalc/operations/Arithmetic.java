package org.polycalc.operations;

import org.polycalc.model.Polynomial;

import java.util.HashMap;
import java.util.List;

public interface Arithmetic {

    Polynomial add(Polynomial p1, Polynomial p2);
    Polynomial subtract(Polynomial p1, Polynomial p2);
    Polynomial multiply(Polynomial p1, Polynomial p2);
    HashMap<String, Polynomial> divide(Polynomial p1, Polynomial p2);
    Polynomial integrate(Polynomial p);
    Polynomial differentiate(Polynomial p);
}
