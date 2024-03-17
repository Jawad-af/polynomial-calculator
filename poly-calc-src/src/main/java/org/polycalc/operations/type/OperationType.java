package org.polycalc.operations.type;

public enum OperationType {
    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
    INTEGRATE("Integrate"),
    DIFFERENTIATE("Differentiate");

    private final String symbol;

    OperationType(String symbol) {
        this.symbol = symbol;
    }
    public static OperationType fromString(String str) {
        for (OperationType operationType : OperationType.values()) {
            if (operationType.symbol.equals(str)) {
                return operationType;
            }
        }
        throw new IllegalArgumentException("Unknown operation type: " + str);
    }
}

