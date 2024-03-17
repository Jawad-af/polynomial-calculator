package org.polycalc.service;

import org.junit.jupiter.api.Test;
import org.polycalc.model.Monomial;
import org.polycalc.model.Polynomial;

class PolyOperationsTest {

    @Test
    void add() {
        // given
        PolyOperations polyOperations = new PolyOperationsImplementation();
        ToString toString = new ToString();
        Polynomial p1 = new Polynomial();
        Polynomial p2 = new Polynomial();

        p1.getTerms().put(3, new Monomial(2, 3));
        p1.getTerms().put(1, new Monomial(-1, 1));
        p1.getTerms().put(0, new Monomial(12, 0));

        p2.getTerms().put(2, new Monomial(4, 2));
        p2.getTerms().put(1, new Monomial(3, 1));
        p2.getTerms().put(0, new Monomial(2, 0));

        // When
        Polynomial result = polyOperations.add(p1, p2);
        String expected = "2.0X\u00B3 +4.0X\u00B2 +2.0X +14.0";
        String actual = toString.convert(result);

        // Then
        assertEquals(expected, toString.convert(result));
    }

}