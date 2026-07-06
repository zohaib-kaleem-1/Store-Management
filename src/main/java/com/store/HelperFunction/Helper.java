package com.store.HelperFunction;

import java.util.List;
import java.util.Scanner;
import java.lang.reflect.Field;

public class Helper {
    public static String inputString(String prompt) {
        Scanner scanner = new Scanner(System.in);
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine();

            if (input != null && !input.trim().isEmpty()) {
                // scanner.close();
                return input;
            }
            Helper.printColored("Invalid input! Please enter non-empty text.", "red");
        }

    }

    public static long inputLong(String prompt) {
        Scanner src = new Scanner(System.in);
        long n;

        while (true) {
            try {
                System.out.print(prompt);
                String input = src.next().trim();

                if (input.isEmpty()) {
                    Helper.printColored("Error: contact no can't be empty.", "red");
                    continue;
                }

                if (!input.matches("\\d+")) {
                    Helper.printColored("Error: Contact number must only contain digits.", "red");
                    continue;
                }

                if (input.length() != 11) {
                    Helper.printColored("Error: Contact number must be of length 11.", "red");
                    continue;
                }

                n = Long.parseLong(input);

                break;
            } catch (NumberFormatException e) {
                Helper.printColored("Invalid Input", "red");
            } catch (Exception e) {
                Helper.printColored("Error taking Input", "red");
            }
        }

        // src.close();
        return n;
    }

    public static int intInput(String a, int minValue, int maxValue) {
        Scanner src = new Scanner(System.in);

        while (true) {
            try {
                int n;
                System.out.print(a);
                n = Integer.parseInt(src.next());

                if (n <= maxValue && n >= minValue) {
                    // src.close();
                    return n;
                }

                Helper.printColored("Invalid Input", "red");
            } catch (Exception e) {
                Helper.printColored("Error Taking Input", "red");
            }
            Helper.printColored("Enter a number between " + minValue + " and " + maxValue, "blue");
        }

    }

    public static <T> void printList(List<T> items) {
        if (items == null || items.isEmpty()) {
            System.out.println("No records to display.");
            return;
        }

        // Get all fields from the class
        Field[] fields = items.get(0).getClass().getDeclaredFields();
        int columnCount = fields.length;

        // Get field names as headers
        String[] headers = new String[columnCount];
        for (int i = 0; i < columnCount; i++) {
            headers[i] = fields[i].getName();
        }

        // Calculate column widths
        int[] widths = new int[columnCount];
        for (int i = 0; i < columnCount; i++) {
            widths[i] = headers[i].length();
        }

        // Make fields accessible
        for (Field field : fields) {
            field.setAccessible(true);
        }

        // Calculate max widths based on data
        for (T item : items) {
            for (int i = 0; i < columnCount; i++) {
                try {
                    Object value = fields[i].get(item);
                    String strValue = value != null ? value.toString() : "null";
                    widths[i] = Math.max(widths[i], strValue.length());
                } catch (IllegalAccessException e) {
                    widths[i] = Math.max(widths[i], 4); // "null".length()
                }
            }
        }

        // Build table
        StringBuilder sb = new StringBuilder();

        // Build format string
        StringBuilder formatBuilder = new StringBuilder("|");
        for (int i = 0; i < columnCount; i++) {
            formatBuilder.append(" %-").append(widths[i]).append("s |");
        }
        formatBuilder.append("\n");
        String format = formatBuilder.toString();

        // Build separator
        StringBuilder separatorBuilder = new StringBuilder("+");
        for (int i = 0; i < columnCount; i++) {
            separatorBuilder.append("-".repeat(widths[i] + 2)).append("+");
        }
        separatorBuilder.append("\n");
        String separator = separatorBuilder.toString();

        // Print table
        sb.append(separator);
        sb.append(String.format(format, (Object[]) headers));
        sb.append(separator);

        for (T item : items) {
            Object[] rowValues = new Object[columnCount];
            for (int i = 0; i < columnCount; i++) {
                try {
                    Object value = fields[i].get(item);
                    rowValues[i] = value != null ? value : "null";
                } catch (IllegalAccessException e) {
                    rowValues[i] = "null";
                }
            }
            sb.append(String.format(format, rowValues));
        }
        sb.append(separator);

        System.out.print(sb);
    }

    public static void printColored(String text, String color) {
        String reset = "\u001B[0m";
        String colorCode = "";

        switch (color.toLowerCase()) {
            case "red":
                colorCode = "\u001B[31m";
                break;
            case "green":
                colorCode = "\u001B[32m";
                break;
            case "yellow":
                colorCode = "\u001B[33m";
                break;
            case "blue":
                colorCode = "\u001B[34m";
                break;
            case "purple":
                colorCode = "\u001B[35m";
                break;
            case "cyan":
                colorCode = "\u001B[36m";
                break;
            case "white":
                colorCode = "\u001B[37m";
                break;
            default:
                colorCode = "\u001B[0m";
                break;
        }

        System.out.println(colorCode + text + reset);
    }
}