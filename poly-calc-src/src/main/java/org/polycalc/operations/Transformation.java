package org.polycalc.operations;

import org.polycalc.model.Polynomial;

public interface Transformation {

    public Polynomial parse(String inputString);
    String convertToString(Polynomial polynomial);
}
