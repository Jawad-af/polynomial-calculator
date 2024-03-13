package org.polycalc.caluculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame {
    private JTextField displayField;
    private JPanel buttonPanel;
    private JButton[] numberButtons;
    private JButton addButton, subtractButton, multiplyButton, divideButton, equalsButton, clearButton;

    private double firstNumber;
    private String operator;

    public Calculator() {
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setLocationRelativeTo(null);

        displayField = new JTextField();
        displayField.setEditable(false);

        buttonPanel = new JPanel(new GridLayout(4, 4));
        numberButtons = new JButton[10];
        for (int i = 0; i < 10; i++) {
            numberButtons[i] = new JButton(String.valueOf(i));
            numberButtons[i].addActionListener(new NumberButtonListener());
            buttonPanel.add(numberButtons[i]);
        }

        addButton = new JButton("+");
        addButton.addActionListener(new OperatorButtonListener());
        subtractButton = new JButton("-");
        subtractButton.addActionListener(new OperatorButtonListener());
        multiplyButton = new JButton("*");
        multiplyButton.addActionListener(new OperatorButtonListener());
        divideButton = new JButton("/");
        divideButton.addActionListener(new OperatorButtonListener());
        equalsButton = new JButton("=");
        equalsButton.addActionListener(new EqualsButtonListener());
        clearButton = new JButton("C");
        clearButton.addActionListener(new ClearButtonListener());

        buttonPanel.add(addButton);
        buttonPanel.add(subtractButton);
        buttonPanel.add(multiplyButton);
        buttonPanel.add(divideButton);
        buttonPanel.add(equalsButton);
        buttonPanel.add(clearButton);

        setLayout(new BorderLayout());
        add(displayField, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }

    private class NumberButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String input = e.getActionCommand();
            displayField.setText(displayField.getText() + input);
        }
    }

    private class OperatorButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            firstNumber = Double.parseDouble(displayField.getText());
            operator = e.getActionCommand();
            displayField.setText("");
        }
    }

    private class EqualsButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            double secondNumber = Double.parseDouble(displayField.getText());
            double result = 0;
            switch (operator) {
                case "+":
                    result = firstNumber + secondNumber;
                    break;
                case "-":
                    result = firstNumber - secondNumber;
                    break;
                case "*":
                    result = firstNumber * secondNumber;
                    break;
                case "/":
                    if (secondNumber != 0) {
                        result = firstNumber / secondNumber;
                    } else {
                        JOptionPane.showMessageDialog(null, "Cannot divide by zero");
                    }
                    break;
            }
            displayField.setText(String.valueOf(result));
        }
    }

    private class ClearButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            displayField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Calculator().setVisible(true);
            }
        });
    }
}
