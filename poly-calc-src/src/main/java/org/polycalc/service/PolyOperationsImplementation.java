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

        while (itr1.hasNext() || itr2.hasNext()) {
            while (monomial1 != null && monomial2 != null) {

                if (monomial1.getExpo() > monomial2.getExpo()) {
                    result.getTerms().put(monomial1.getExpo(), monomial1);
                    monomial1 = itr1.hasNext() ? itr1.next().getValue() : null;
                } else if (monomial1.getExpo() < monomial2.getExpo()) {
                    result.getTerms().put(monomial2.getExpo(), monomial2);
                    monomial2 = itr2.hasNext() ? itr2.next().getValue() : null;
                } else {
                    Monomial monomial = new Monomial();
                    monomial.setCoeff(monomial1.getCoeff() + monomial2.getCoeff());
                    monomial.setExpo(monomial1.getExpo());
                    monomial.setVar(monomial1.getVar());
                    result.getTerms().put(monomial.getExpo(), monomial);
                    monomial1 = itr1.hasNext() ? itr1.next().getValue() : null;
                    monomial2 = itr2.hasNext() ? itr2.next().getValue() : null;
                }
            }
            while (monomial1 != null) {
                result.getTerms().put(monomial1.getExpo(), monomial1);
                monomial1 = itr1.hasNext() ? itr1.next().getValue() : null;
            }
            while (monomial2 != null) {
                result.getTerms().put(monomial2.getExpo(), monomial2);
                monomial2 = itr2.hasNext() ? itr2.next().getValue() : null;
            }
        }
        return result;
    }
    @Override
    public Polynomial subtract(Polynomial p1, Polynomial p2) {
        Polynomial p3 = multiplyWithScalar(p2, -1);
        p3 = add(p1, p2);
        return p3;
    }

    @Override
    public Polynomial multiply(Polynomial p1, Polynomial p2) {

        Iterator<Map.Entry<Integer, Monomial>> iterator1 = p1.getTerms().entrySet().iterator();
        Polynomial result = new Polynomial();
        while(iterator1.hasNext()){
            Polynomial interPoly = new Polynomial();
            Monomial m1 = iterator1.next().getValue();
            Iterator<Map.Entry<Integer, Monomial>> iterator2 = p2.getTerms().entrySet().iterator();
            while (iterator2.hasNext()) {
                Monomial m2 = iterator2.next().getValue();
                Monomial m3 = multiplyMonomial(m1, m2);
                interPoly.getTerms().put(m3.getExpo(),m3);
            }
            result = add(result, interPoly);
        }
        return result;
    }

    private Polynomial multiplyWithScalar(Polynomial polynomial, double value) {
        Iterator<Map.Entry<Integer, Monomial>> iterator = polynomial.getTerms().entrySet().iterator();
        while (iterator.hasNext()) {
            Monomial mo = iterator.next().getValue();
            mo.setCoeff(mo.getCoeff() * value);
        }
        return polynomial;
    }

    private Monomial multiplyMonomial(Monomial m1, Monomial m2) {
        Monomial m3 = new Monomial();
        m3.setCoeff(m1.getCoeff() * m2.getCoeff());
        m3.setExpo(m1.getExpo() + m2.getExpo());
        return m3;
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
