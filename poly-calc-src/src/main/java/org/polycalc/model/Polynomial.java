package org.polycalc.model;

import org.polycalc.util.SuperScript;

import java.util.*;

public class Polynomial {

    private NavigableMap<Integer, Monomial> terms = new TreeMap<>(Collections.reverseOrder());

    public Monomial getHigherDegree(){
        return terms.firstEntry().getValue();
        }

    public NavigableMap<Integer, Monomial> getTerms() {
        return terms;
    }

}






