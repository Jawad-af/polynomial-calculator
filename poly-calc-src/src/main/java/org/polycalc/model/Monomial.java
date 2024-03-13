package org.polycalc.model;

public class Monomial {

    private double coeff;
    private Character var;
    private int expo;

    public Monomial(){}
    public Monomial(double coeff, char var, int expo) {
        this.coeff = coeff;
        this.expo = expo;
        this.var = var;
    }

    public Monomial(int coeff, char var, int expo) {
        this.coeff = coeff;
        this.expo = expo;
        this.var = var;
    }

    public double getCoeff() {
        return coeff;
    }

    public void setCoeff(double coeff) {
        this.coeff = coeff;
    }

    public int getExpo() {
        return expo;
    }

    public void setExpo(int expo) {
        this.expo = expo;
    }

    public char getVar() {
        return var;
    }

    public void setVar(char var) {
        this.var = var;
    }
}
