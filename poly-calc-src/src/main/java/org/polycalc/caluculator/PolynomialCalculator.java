package org.polycalc.calculator;

import org.polycalc.model.Polynomial;
import org.polycalc.operations.Arithmetic;
import org.polycalc.operations.Transformation;
import org.polycalc.polyopsimplementaion.PolynomialArithmetic;
import org.polycalc.polyopsimplementaion.PolynomialTransformation;
import org.polycalc.service.PolyOperations;
import org.polycalc.service.PolyOperationsImplementation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class PolynomialCalculator extends JFrame {
    private JTextField firstPolyField;
    private JTextField secondPolyField;
    private JTextField resultField;
    private JPanel buttonPanel;
    private JButton addButton, subtractButton, multiplyButton, divideButton, integrateButton, differentiateButton, clearButton;
    private Polynomial firstPoly;
    private Polynomial secondPoly;

    public PolynomialCalculator() {
        setTitle("Polynomial Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 200);
        setLocationRelativeTo(null);

        firstPolyField = new JTextField();
        secondPolyField = new JTextField();
        resultField = new JTextField();
        resultField.setEditable(false);

        buttonPanel = new JPanel(new GridLayout(3, 3));
        addButton = new JButton("+");
        addButton.addActionListener(new OperatorButtonListener());
        subtractButton = new JButton("-");
        subtractButton.addActionListener(new OperatorButtonListener());
        multiplyButton = new JButton("*");
        multiplyButton.addActionListener(new OperatorButtonListener());
        divideButton = new JButton("/");
        divideButton.addActionListener(new OperatorButtonListener());
        integrateButton = new JButton("Integrate");
        integrateButton.addActionListener(new OperatorButtonListener());
        differentiateButton = new JButton("Differentiate");
        differentiateButton.addActionListener(new OperatorButtonListener());
        clearButton = new JButton("C");
        clearButton.addActionListener(new ClearButtonListener());

        buttonPanel.add(addButton);
        buttonPanel.add(subtractButton);
        buttonPanel.add(multiplyButton);
        buttonPanel.add(divideButton);
        buttonPanel.add(integrateButton);
        buttonPanel.add(differentiateButton);
        buttonPanel.add(clearButton);

        setLayout(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("First Polynomial:"));
        inputPanel.add(firstPolyField);
        inputPanel.add(new JLabel("Second Polynomial:"));
        inputPanel.add(secondPolyField);

        add(inputPanel, BorderLayout.NORTH);
        add(resultField, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private class OperatorButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String firstPolyString = firstPolyField.getText();
            String secondPolyString = secondPolyField.getText();

            Transformation transformation = new PolynomialTransformation();
            Arithmetic arithmetic = new PolynomialArithmetic();

            PolyOperations polyOperations = new PolyOperationsImplementation();

            firstPoly = transformation.parse(firstPolyString);
            secondPoly = transformation.parse(secondPolyString);

            Polynomial result = new Polynomial();
            HashMap<String, Polynomial> divisionResult = new HashMap<>();
            StringBuilder output = new StringBuilder();
            switch (e.getActionCommand()) {
                case "+":
                    result = polyOperations.add(firstPoly, secondPoly);
                    output.append(transformation.convertToString((result)));
                    break;
                case "-":
                    result = polyOperations.subtract(firstPoly, secondPoly);
                    output.append(transformation.convertToString((result)));
                    break;
                case "*":
                    result = polyOperations.multiply(firstPoly, secondPoly);
                    output.append(transformation.convertToString((result)));
                    break;
                case "/":
                    divisionResult = polyOperations.divide(firstPoly, secondPoly);
                    result = divisionResult.get("result");
                    output.append(transformation.convertToString((result)));
                    output.append(" + ");
                    result = divisionResult.get("reminder");
                    output.append("(" + transformation.convertToString((result)) + ")/");
                    result = divisionResult.get("divisor");
                    output.append("(" + transformation.convertToString((result)) + ")");
                    break;
                case "Integrate":
                    result = polyOperations.integrate(firstPoly);
                    output.append(transformation.convertToString((result)));
                    break;
                case "Differentiate":
                    result = polyOperations.differentiate(firstPoly);
                    output.append(transformation.convertToString((result)));
                    break;
            }
            resultField.setText(output.toString());
        }
    }

    private class ClearButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            firstPolyField.setText("");
            secondPolyField.setText("");
            resultField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PolynomialCalculator().setVisible(true);
            }
        });
    }
}
