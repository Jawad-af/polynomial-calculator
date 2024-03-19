package org.polycalc.model;

import org.polycalc.globals.Variable;
import org.polycalc.util.SuperScript;

import java.util.*;

public class Polynomial {

    private NavigableMap<Integer, Monomial> terms = new TreeMap<>(Collections.reverseOrder());

    public int getHigherDegree(){
        if(terms.size() > 0)
            return terms.firstEntry().getKey();
        return 0;
    }

    public NavigableMap<Integer, Monomial> getTerms() {
        return terms;
    }

}






