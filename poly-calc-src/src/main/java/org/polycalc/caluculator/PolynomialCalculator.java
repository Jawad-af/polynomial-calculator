package org.polycalc.calculator;

import org.polycalc.model.Polynomial;
import org.polycalc.service.PolyOperations;
import org.polycalc.service.PolyOperationsImplementation;
import org.polycalc.util.PolyParse;
import org.polycalc.util.ToString;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PolynomialCalculator extends JFrame {
    private JTextField firstPolyField;
    private JTextField secondPolyField;
    private JTextField resultField;
    private JPanel buttonPanel;
    private JButton addButton, subtractButton, multiplyButton, divideButton, clearButton;

    private Polynomial firstPoly;
    private Polynomial secondPoly;

    public PolynomialCalculator() {
        setTitle("Polynomial Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        firstPolyField = new JTextField();
        secondPolyField = new JTextField();
        resultField = new JTextField();
        resultField.setEditable(false);

        buttonPanel = new JPanel(new GridLayout(2, 4));
        addButton = new JButton("+");
        addButton.addActionListener(new OperatorButtonListener());
        subtractButton = new JButton("-");
        subtractButton.addActionListener(new OperatorButtonListener());
        multiplyButton = new JButton("*");
        multiplyButton.addActionListener(new OperatorButtonListener());
        divideButton = new JButton("/");
        divideButton.addActionListener(new OperatorButtonListener());
        clearButton = new JButton("C");
        clearButton.addActionListener(new ClearButtonListener());

        buttonPanel.add(addButton);
        buttonPanel.add(subtractButton);
        buttonPanel.add(multiplyButton);
        buttonPanel.add(divideButton);
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

            PolyParse parse = new PolyParse();
            ToString toString = new ToString();
            PolyOperations polyOperations = new PolyOperationsImplementation();

            firstPoly = parse.extractPoly(firstPolyString);
            secondPoly = parse.extractPoly(secondPolyString);

            Polynomial result = new Polynomial();

            switch (e.getActionCommand()) {
                case "+":
                    result = polyOperations.add(firstPoly, secondPoly);
                    break;
                case "-":
                    result = polyOperations.subtract(firstPoly, secondPoly);
                    break;
                case "*":
                    result = polyOperations.multiply(firstPoly, secondPoly);
                    break;
                case "/":
                    // Division operation implementation here
                    break;
            }
            String output = toString.convert(result);
            resultField.setText(output);
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
