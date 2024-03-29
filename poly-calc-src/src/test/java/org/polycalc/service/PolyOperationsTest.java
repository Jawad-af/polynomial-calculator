package org.polycalc.service;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.polycalc.model.Monomial;
import org.polycalc.model.Polynomial;
import org.polycalc.operations.api.Arithmetic;
import org.polycalc.operations.api.Transformation;
import org.polycalc.operations.service.PolynomialArithmetic;
import org.polycalc.operations.service.PolynomialTransformation;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PolyOperationsTest {

    Transformation transformation = new PolynomialTransformation();
    Arithmetic arithmetic = new PolynomialArithmetic();

    Polynomial p1;
    Polynomial p2;

    @BeforeEach
    public void initialize() {
        transformation = new PolynomialTransformation();
        arithmetic = new PolynomialArithmetic();
        p1 = new Polynomial();
        p2 = new Polynomial();
    }

    @Test
    void additionCase0() {

        // 4x^3 + 2x^2 - 5
        p1.getTerms().put(3, new Monomial(4, 3));
        p1.getTerms().put(2, new Monomial(2, 2));
        p1.getTerms().put(0, new Monomial(-5, 0));

        // -2x^2 + 3x + 1
        p2.getTerms().put(2, new Monomial(-2, 2));
        p2.getTerms().put(1, new Monomial(3, 1));
        p2.getTerms().put(0, new Monomial(1, 0));

        Polynomial result = arithmetic.add(p1, p2);
        String expected = "4.0X\u00B3 +3.0X -4.0";
        String actual = transformation.convertToString(result);

        assertEquals(expected, actual);
    }

    @Test
    void additionCase1() {

        // 2x^2 - 3x + 4
        p1.getTerms().put(2, new Monomial(2, 2));
        p1.getTerms().put(1, new Monomial(-3, 1));
        p1.getTerms().put(0, new Monomial(4, 0));

        // 5x^3 + 2x - 1
        p2.getTerms().put(3, new Monomial(5, 3));
        p2.getTerms().put(1, new Monomial(2, 1));
        p2.getTerms().put(0, new Monomial(-1, 0));

        Polynomial result = arithmetic.add(p1, p2);
        String expected = "5.0X\u00B3 +2.0X\u00B2 -1.0X +3.0";
        String actual = transformation.convertToString(result);

        assertEquals(expected, actual);
    }

    @Test
    void subtractionCase0() {

        // 2X^3 - X + 12
        p1.getTerms().put(3, new Monomial(2, 3));
        p1.getTerms().put(1, new Monomial(-1, 1));
        p1.getTerms().put(0, new Monomial(12, 0));

        // 4X^2 + 3X + 2
        p2.getTerms().put(2, new Monomial(4, 2));
        p2.getTerms().put(1, new Monomial(3, 1));
        p2.getTerms().put(0, new Monomial(2, 0));


        Polynomial result = arithmetic.subtract(p1, p2);
        String expected = "2.0X\u00B3 -4.0X\u00B2 -4.0X +10.0";
        String actual = transformation.convertToString(result);

        assertEquals(expected, actual);
    }

    @Test
    void subtractionCase1() {

        // 4x^3 + 2x^2 - 5
        p1.getTerms().put(3, new Monomial(4, 3));
        p1.getTerms().put(2, new Monomial(2, 2));
        p1.getTerms().put(0, new Monomial(-5, 0));

        // -2x^2 + 3x + 1
        p2.getTerms().put(2, new Monomial(-2, 2));
        p2.getTerms().put(1, new Monomial(3, 1));
        p2.getTerms().put(0, new Monomial(1, 0));

        Polynomial result = arithmetic.subtract(p1, p2);
        String expected = "4.0X\u00B3 +4.0X\u00B2 -3.0X -6.0";
        String actual = transformation.convertToString(result);

        assertEquals(expected, actual);
    }

    @Test
    void subtractionCase2() {

        // 2x^2 - 3x + 4
        p1.getTerms().put(2, new Monomial(2, 2));
        p1.getTerms().put(1, new Monomial(-3, 1));
        p1.getTerms().put(0, new Monomial(4, 0));

        // 5x^3 + 2x - 1
        p2.getTerms().put(3, new Monomial(5, 3));
        p2.getTerms().put(1, new Monomial(2, 1));
        p2.getTerms().put(0, new Monomial(-1, 0));

        Polynomial result = arithmetic.subtract(p1, p2);
        String expected = "-5.0X\u00B3 +2.0X\u00B2 -5.0X +5.0";
        String actual = transformation.convertToString(result);

        assertEquals(expected, actual);
    }

    @Test
    void subtractionCase3() {

        // 2X^3 - X + 12
        p1.getTerms().put(3, new Monomial(2, 3));
        p1.getTerms().put(1, new Monomial(-1, 1));
        p1.getTerms().put(0, new Monomial(12, 0));

        // 4X^2 + 3X + 2
        p2.getTerms().put(2, new Monomial(4, 2));
        p2.getTerms().put(1, new Monomial(3, 1));
        p2.getTerms().put(0, new Monomial(2, 0));


        Polynomial result = arithmetic.add(p1, p2);
        String expected = "2.0X\u00B3 +4.0X\u00B2 +2.0X +14.0";
        String actual = transformation.convertToString(result);

        assertEquals(expected, actual);
    }

    @Test
    void multiplication0() {

        // 2x^2-3x^3+2
        p1.getTerms().put(2, new Monomial(2, 2));
        p1.getTerms().put(3, new Monomial(-3, 3));
        p1.getTerms().put(0, new Monomial(2, 0));

        // 3X^2 - 2
        p2.getTerms().put(2, new Monomial(3, 2));
        p2.getTerms().put(0, new Monomial(-2, 0));


        Polynomial result = arithmetic.multiply(p1, p2);
        String expected = "-9.0X\u2075 +6.0X\u2074 +6.0X\u00B3 +2.0X\u00B2 -4.0";
        String actual = transformation.convertToString(result);

        assertEquals(expected, actual);
    }

    @Test
    void multiplication1() {

        // 4x^3 + 2x^2 - 5
        p1.getTerms().put(3, new Monomial(4, 3));
        p1.getTerms().put(2, new Monomial(2, 2));
        p1.getTerms().put(0, new Monomial(-5, 0));

        // -2x^2 + 3
        p2.getTerms().put(2, new Monomial(-2, 2));
        p2.getTerms().put(0, new Monomial(3, 0));

        Polynomial result = arithmetic.multiply(p1, p2);
        String expected = "-8.0X\u2075 -4.0X\u2074 +12.0X\u00B3 +16.0X\u00B2 -15.0";
        String actual = transformation.convertToString(result);

        assertEquals(expected, actual);
    }

    @Test
    void multiplication2() {

        // -3x^2 + 5x - 7
        p1.getTerms().put(2, new Monomial(-3, 2));
        p1.getTerms().put(1, new Monomial(5, 1));
        p1.getTerms().put(0, new Monomial(-7, 0));

        // -2x + 4
        p2.getTerms().put(1, new Monomial(-2, 1));
        p2.getTerms().put(0, new Monomial(4, 0));

        Polynomial result = arithmetic.multiply(p1, p2);
        String expected = "6.0X\u00B3 -22.0X\u00B2 +34.0X -28.0";
        String actual = transformation.convertToString(result);

        assertEquals(expected, actual);
    }


    @Test
    void divide1() {

        // x^3 - 2x^2 + 0x -4
        p1.getTerms().put(3, new Monomial(1, 3));
        p1.getTerms().put(2, new Monomial(-2, 2));
        p1.getTerms().put(1, new Monomial(0, 1));
        p1.getTerms().put(0, new Monomial(-4, 0));


        // x - 3
        p2.getTerms().put(1, new Monomial(1, 1));
        p2.getTerms().put(0, new Monomial(-3, 0));


        HashMap<String, Polynomial> divisionResult = arithmetic.divide(p1, p2);
        Polynomial result = divisionResult.get("result");
        Polynomial reminder = divisionResult.get("reminder");
        Polynomial divisor = divisionResult.get("divisor");

        StringBuilder output = new StringBuilder();
        output.append(transformation.convertToString(result));
        output.append(" + ");
        output.append("(" + transformation.convertToString(reminder) + ")/");
        output.append("(" + transformation.convertToString(divisor) + ")");

        String expected = "X\u00B2 +X +3.0 + (5.0)/(X -3.0)";
        String actual = output.toString();

        assertEquals(expected, actual);
    }

    @Test
    void divide2() {

        // 4x^2 + 2x -3
        p1.getTerms().put(2, new Monomial(4, 2));
        p1.getTerms().put(1, new Monomial(2, 1));
        p1.getTerms().put(0, new Monomial(-3, 0));

        // -X + 2
        p2.getTerms().put(1, new Monomial(-1, 1));
        p2.getTerms().put(0, new Monomial(+2, 0));


        HashMap<String, Polynomial> divisionResult = arithmetic.divide(p1, p2);
        Polynomial result = divisionResult.get("result");
        Polynomial reminder = divisionResult.get("reminder");
        Polynomial divisor = divisionResult.get("divisor");

        StringBuilder output = new StringBuilder();
        output.append(transformation.convertToString(result));
        output.append(" + ");
        output.append("(" + transformation.convertToString(reminder) + ")/");
        output.append("(" + transformation.convertToString(divisor) + ")");

        String expected = "-4.0X -10.0 + (17.0)/(-1.0X +2.0)";
        String actual = output.toString();

        assertEquals(expected, actual);
    }
    @Test
    void divide3() {

        // 2x^3 - 8x^2 - 6x + 10
        p1.getTerms().put(3, new Monomial(2, 3));
        p1.getTerms().put(2, new Monomial(8, 2));
        p1.getTerms().put(1, new Monomial(-6, 1));
        p1.getTerms().put(0, new Monomial(10, 0));

        // X - 3
        p2.getTerms().put(1, new Monomial(1, 1));
        p2.getTerms().put(0, new Monomial(-2, 0));


        HashMap<String, Polynomial> divisionResult = arithmetic.divide(p1, p2);
        Polynomial result = divisionResult.get("result");
        Polynomial reminder = divisionResult.get("reminder");
        Polynomial divisor = divisionResult.get("divisor");

        StringBuilder output = new StringBuilder();
        output.append(transformation.convertToString(result));
        output.append(" + ");
        output.append("(" + transformation.convertToString(reminder) + ")/");
        output.append("(" + transformation.convertToString(divisor) + ")");

        String expected = "2.0X\u00B2 +12.0X +18.0 + (46.0)/(X -2.0)";
        String actual = output.toString();

        assertEquals(expected, actual);
    }
    @Test
    void integrate() {

        // p1 = -4x^3 + 6x^2 + 2
        p1.getTerms().put(3, new Monomial(-4, 3));
        p1.getTerms().put(2, new Monomial(6, 2));
        p1.getTerms().put(0, new Monomial(2, 0));

        String expected = "-1.0X\u2074 +2.0X\u00B3 +2.0X";
        String actual = transformation.convertToString(arithmetic.integrate(p1));

        assertEquals(expected, actual);
    }

    @Test
    void differentiate() {

        // p1 = -4x^3 + 6x^2 + 2
        p1.getTerms().put(3, new Monomial(-4, 3));
        p1.getTerms().put(2, new Monomial(6, 2));
        p1.getTerms().put(0, new Monomial(2, 0));

        String expected = "-12.0X\u00B2 +12.0X";
        String actual = transformation.convertToString(arithmetic.differentiate(p1));

        assertEquals(expected, actual);
    }
}