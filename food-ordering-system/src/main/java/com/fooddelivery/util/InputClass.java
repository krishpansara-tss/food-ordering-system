package com.fooddelivery.util;

import java.util.Scanner;

public class InputClass {
    
    public static int readInt(Scanner scanner, String message, int min, int max) {
        while (true) {
            System.out.print(message);
            try {
                int value = scanner.nextInt();
                scanner.nextLine();
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("Invalid selection. Please enter a number between " + min + " and " + max + ".");
            } catch (Exception e) {
                System.out.println("Invalid input format. Please enter a valid integer.");
                scanner.nextLine();
            }
        }
    }
    
    public static double readDouble(Scanner scanner, String message, double min) {
        while (true) {
            System.out.print(message);
            try {
                double value = scanner.nextDouble();
                scanner.nextLine();
                if (value >= min) {
                    return value;
                }
                System.out.printf("Value must be at least %.2f. Please try again.\n", min);
            } catch (Exception e) {
                System.out.println("Invalid input format. Please enter a valid decimal number.");
                scanner.nextLine();
            }
        }
    }
    
    public static String readString(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty. Please enter a valid string.");
        }
    }

    public static boolean readBoolean(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("true") || input.equals("yes") || input.equals("y") || input.equals("t")) {
                return true;
            }
            if (input.equals("false") || input.equals("no") || input.equals("n") || input.equals("f")) {
                return false;
            }
            System.out.println("Invalid response. Please enter true/yes or false/no.");
        }
    }
}
