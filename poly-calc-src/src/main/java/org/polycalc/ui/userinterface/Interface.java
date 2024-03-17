package org.polycalc.ui.userinterface;

import org.polycalc.model.Polynomial;
import org.polycalc.operations.type.OperationType;
import org.polycalc.ui.service.OperationsHandling;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Interface extends JFrame {
    private JTextField firstPolyField;
    private JTextField secondPolyField;
    private JTextField resultField;
    private JPanel buttonPanel;
    private JButton addButton, subtractButton, multiplyButton, divideButton, integrateButton, differentiateButton, clearButton;
    private Polynomial firstPoly;
    private Polynomial secondPoly;

    public Interface() {
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
        inputPanel.add(new JLabel("First Polynomial | Integration:"));
        inputPanel.add(firstPolyField);
        inputPanel.add(new JLabel("Second Polynomial | Differentiation:"));
        inputPanel.add(secondPolyField);

        add(inputPanel, BorderLayout.NORTH);
        add(resultField, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private class OperatorButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String firstPolyString = firstPolyField.getText();
            String secondPolyString = secondPolyField.getText();

            OperationType operationType = getOperationType(e.getActionCommand());
            StringBuilder output = new StringBuilder();

            OperationsHandling operationsHandler = new OperationsHandling();
            output = operationsHandler.execute(firstPolyString, secondPolyString, operationType);
            resultField.setText(output.toString());
        }
    }

    private OperationType getOperationType(String actionCommand) {
        return OperationType.fromString(actionCommand);
    }

    private class ClearButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            firstPolyField.setText("");
            secondPolyField.setText("");
            resultField.setText("");
        }
    }
}
