package org.polycalc.service;

import org.polycalc.model.Monomial;
import org.polycalc.model.Polynomial;

import java.util.Iterator;
import java.util.Map;

public class PolyOperationsImplementation implements PolyOperations {

    // this implementation will be changed the reason is that its a trivial one ( using regex would be more pro and efficient )
    @Override
    public Polynomial add(Polynomial p1, Polynomial p2) {
        Iterator<Map.Entry<Integer, Monomial>> itr1 = p1.getTerms().entrySet().iterator();
        Iterator<Map.Entry<Integer, Monomial>> itr2 = p2.getTerms().entrySet().iterator();
        Polynomial result = new Polynomial();

        Monomial monomial1 = itr1.hasNext() ? itr1.next().getValue() : null;
        Monomial monomial2 = itr2.hasNext() ? itr2.next().getValue() : null;

        if (p1.getHigherDegree() > p2.getHigherDegree()) {
            while (itr2.hasNext()) {
                Monomial term = itr2.next().getValue();
                if (p1.getTerms().containsKey(term.getExpo())) {

                }
//                StringBuilder pattern = new StringBuilder();
//                pattern.append(term.getCoeff());
//                pattern.append(term.getVar());
//                pattern.append(term.getExpo());
//
            }
        }

        while (itr1.hasNext()) {

            if (monomial1.getExpo() > monomial2.getExpo()) {
                result.getTerms().put(monomial1.getExpo(), monomial1);
                monomial1 = itr1.next().getValue();
            } else if (monomial1.getExpo() < monomial2.getExpo()) {
                result.getTerms().put(monomial2.getExpo(), monomial2);
                monomial2 = itr2.next().getValue();
            }else {
                Monomial monomial = new Monomial();
                monomial.setCoeff(monomial1.getCoeff() + monomial2.getCoeff());
                monomial.setExpo(monomial1.getExpo());
                monomial.setVar(monomial1.getVar());
                result.getTerms().put(monomial.getExpo(), monomial);
                monomial1 = itr1.next().getValue();
                monomial2 = itr2.next().getValue();
            }
        }
        return result;
    }

    @Override
    public Polynomial subtract(Polynomial p1, Polynomial p2) {
        return null;
    }

    @Override
    public Polynomial multiply(Polynomial p1, Polynomial p2) {
        return null;
    }

    @Override
    public Polynomial divide(Polynomial p1, Polynomial p2) {
        return null;
    }

    @Override
    public Polynomial integrate(Polynomial p) {
        return null;
    }

    @Override
    public Polynomial derive(Polynomial p) {
        return null;
    }
}
