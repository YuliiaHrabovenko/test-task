package com.test.task.util;

public class StringUtil {
    public static String toCamelCaseWithSingleSpaces(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String normalizedInput = input.trim().replaceAll("\\s+", " ");
        String[] words = normalizedInput.split(" ");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                result.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return result.toString().trim();
    }
}
