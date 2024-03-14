package org.polycalc.model;

import org.polycalc.globals.Variable;

public class Monomial {
    private double coeff;
    public static Variable var;
    private int expo;

    public Monomial(){
        this.coeff = 1.0;
        var = Variable.getInstance();
        this.expo = 1;
    }

    public Monomial(double coeff, Variable var, int expo) {
        this.coeff = coeff;
        this.var = var;
        this.expo = expo;
    }

    public Monomial(int coeff, Variable var, int expo) {
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

    public Variable getVar() {
        return var;
    }

    public void setVar(Variable var) {
        this.var = var;
    }
}
