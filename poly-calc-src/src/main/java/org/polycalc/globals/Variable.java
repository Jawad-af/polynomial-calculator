package org.polycalc.globals;

// Singletone design patten is used in order to maintain only one instance of the variable x
public class Variable {
    private String name;
    private double value;
    private static Variable instance;

    private Variable() {
        this.name = "X";
        this.value = 1.0;
    }

    public static Variable getInstance() {
        if (instance == null) {
            return new Variable();
        } else return instance;
    }

    public String getName() {
        if (instance == null) {
            instance = new Variable();
            return instance.name;
        }
        return instance.name;
    }

    public double getValue() {
        if (instance == null) {
            instance = new Variable();
            return instance.value;
        }
        return instance.value;
    }

    public void setName(String name) {
        instance.name = name;
    }

    public void setValue(double value) {
        instance.value = value;
    }

}
