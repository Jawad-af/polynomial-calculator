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
        setSize(500, 300);
        setLocationRelativeTo(null);

        // Create text fields with preferred sizes
        firstPolyField = new JTextField();
        firstPolyField.setPreferredSize(new Dimension(200, 30)); // Adjust the preferred size as needed
        secondPolyField = new JTextField();
        secondPolyField.setPreferredSize(new Dimension(200, 30)); // Adjust the preferred size as needed
        resultField = new JTextField();
        resultField.setEditable(false);
        resultField.setPreferredSize(new Dimension(400, 30)); // Adjust the preferred size as needed

        // Create button panel
        buttonPanel = new JPanel(new GridLayout(3, 3));
        addButton = new JButton("+");
        addButton.addActionListener(new OperatorButtonListener());
        setComponentMargins(addButton); // Add margins to addButton
        subtractButton = new JButton("-");
        subtractButton.addActionListener(new OperatorButtonListener());
        setComponentMargins(subtractButton); // Add margins to subtractButton
        multiplyButton = new JButton("*");
        multiplyButton.addActionListener(new OperatorButtonListener());
        setComponentMargins(multiplyButton); // Add margins to multiplyButton
        divideButton = new JButton("/");
        divideButton.addActionListener(new OperatorButtonListener());
        setComponentMargins(divideButton); // Add margins to divideButton
        integrateButton = new JButton("Integrate");
        integrateButton.addActionListener(new OperatorButtonListener());
        setComponentMargins(integrateButton); // Add margins to integrateButton
        differentiateButton = new JButton("Differentiate");
        differentiateButton.addActionListener(new OperatorButtonListener());
        setComponentMargins(differentiateButton); // Add margins to differentiateButton
        clearButton = new JButton("C");
        clearButton.addActionListener(new ClearButtonListener());
        setComponentMargins(clearButton); // Add margins to clearButton

        buttonPanel.add(addButton);
        buttonPanel.add(subtractButton);
        buttonPanel.add(multiplyButton);
        buttonPanel.add(divideButton);
        buttonPanel.add(integrateButton);
        buttonPanel.add(differentiateButton);
        buttonPanel.add(clearButton);

        // Create input panel
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10)); // Set horizontal and vertical gap
        inputPanel.add(new JLabel("First Polynomial | Integration:"));
        inputPanel.add(firstPolyField);
        inputPanel.add(new JLabel("Second Polynomial | Differentiation:"));
        inputPanel.add(secondPolyField);

        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10)); // Set horizontal and vertical gap
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding to main panel
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(resultField, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
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

    // Method to set margins for components
    private void setComponentMargins(Component component) {
        Insets insets = new Insets(5, 5, 5, 5); // Adjust insets as needed
        ((JButton) component).setMargin(insets);
    }
}
