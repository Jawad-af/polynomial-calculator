package org.polycalc.util;

import org.junit.jupiter.api.Test;
import org.polycalc.model.Monomial;
import org.polycalc.model.Polynomial;

import static org.junit.jupiter.api.Assertions.*;

class ToStringTest {


    @Test
    void convert() {
        ToString toString = new ToString();
        //given
        Polynomial polynomial = new Polynomial();
        polynomial.getTerms().put(3, new Monomial(2, 3));
        polynomial.getTerms().put(1, new Monomial(-1, 1));
        polynomial.getTerms().put(0, new Monomial(12, 0));

        //when
        String s = toString.convert(polynomial);

        //then
        assertEquals("2.0X\u00B3 -1.0X +12.0", s);
    }
}