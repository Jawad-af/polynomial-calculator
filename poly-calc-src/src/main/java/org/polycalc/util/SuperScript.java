package org.polycalc.util;

public class SuperScript {

    // Method to convert a number to superscript Unicode characters
    public static String convert(int number) {
        String[] digits = {"\u2070", "\u00B9", "\u00B2", "\u00B3", "\u2074", "\u2075", "\u2076", "\u2077", "\u2078", "\u2079"};
        StringBuilder result = new StringBuilder();
        String numStr = String.valueOf(number);
        for (int i = 0; i < numStr.length(); i++) {
            int digit = Character.getNumericValue(numStr.charAt(i));
            result.append(digits[digit]);
        }
        return result.toString();
    }
}
