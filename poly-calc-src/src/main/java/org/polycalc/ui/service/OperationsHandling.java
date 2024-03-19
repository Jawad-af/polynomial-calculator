package org.polycalc.ui.service;

import org.polycalc.model.Polynomial;
import org.polycalc.operations.api.Arithmetic;
import org.polycalc.operations.api.Transformation;
import org.polycalc.operations.service.PolynomialArithmetic;
import org.polycalc.operations.service.PolynomialTransformation;
import org.polycalc.operations.type.OperationType;
import javax.swing.JOptionPane;
;
import java.util.HashMap;
import static org.polycalc.operations.type.OperationType.*;

public class OperationsHandling {

    public StringBuilder execute(String firstPolyString, String secondPolyString, OperationType operationType) {
        Transformation transformation = new PolynomialTransformation();
        Arithmetic arithmetic = new PolynomialArithmetic();

        Polynomial firstPoly = transformation.parse(firstPolyString);
        Polynomial secondPoly = transformation.parse(secondPolyString);
        Polynomial result = new Polynomial();
        StringBuilder output = new StringBuilder();

        switch (operationType) {
            case INTEGRATE:
                result = arithmetic.integrate(firstPoly);
                output.append(transformation.convertToString(result));
                return output;
            case DIFFERENTIATE:
                result = arithmetic.differentiate(secondPoly);
                output.append(transformation.convertToString(result));
                return output;
        }

        if (!firstPoly.getTerms().isEmpty() && !secondPoly.getTerms().isEmpty()) {
            switch (operationType) {
                    case ADD:
                        result = arithmetic.add(firstPoly, secondPoly);
                        break;
                    case SUBTRACT:
                        result = arithmetic.subtract(firstPoly, secondPoly);
                        break;
                    case MULTIPLY:
                        result = arithmetic.multiply(firstPoly, secondPoly);
                        break;
                    case DIVIDE:
                        if (firstPoly.getHigherDegree() < secondPoly.getHigherDegree()) {
                            output.append("The degree of the numerator should be higher!");
                        } else {
                            output = handleDivision(arithmetic, firstPoly, secondPoly, transformation);
                        }
                        break;
                    }
        } else {
            output.append("Invalid input! Please provide two valid operands");
        }

        if (operationType == DIVIDE) {
            return output;
        } else {
            output.append(transformation.convertToString(result));
        }
        return output;
    }

    private StringBuilder handleDivision(Arithmetic arithmetic, Polynomial firstPoly,
                                         Polynomial secondPoly, Transformation transformation) {
        HashMap<String, Polynomial> divisionResult = arithmetic.divide(firstPoly, secondPoly);
        Polynomial result = divisionResult.get("result");
        Polynomial reminder = divisionResult.get("reminder");
        Polynomial divisor = divisionResult.get("divisor");

        StringBuilder output = new StringBuilder();
        output.append(transformation.convertToString(result));
        if (!(transformation.convertToString(reminder) == "")) {
            output.append("+ (").append(transformation.convertToString(reminder)).append(")/");
            output.append("(").append(transformation.convertToString(divisor)).append(")");
        }

        return output;
    }

    public void displayError(String toBeDisplayed) {
        JOptionPane.showMessageDialog(null, toBeDisplayed, "Error", JOptionPane.ERROR_MESSAGE);
    }

}


