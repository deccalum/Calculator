package se.lexicon;

import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        
        System.out.printf("Calculator%n");
        boolean run = true;
        Scanner scanner = new Scanner(System.in);
        while (run) {
            System.out.println();
                System.out.printf("Enter your first number: ");
                if (scanner.hasNext("X")) {
                    run = false;
                    continue;
                }
                double num1 = scanner.nextDouble();
                System.out.print("Enter operator (+, -, *, /): ");
                String operator = scanner.next();
                System.out.printf("Enter your second number: ");
                double num2 = scanner.nextDouble();

            switch (operator) {
                case "+":
                    System.out.println();
                    System.out.printf("Result: %.2f%n", num1 + num2);
                    break;
                case "-":
                    System.out.println();
                    System.out.printf("Result: %.2f%n", num1 - num2);
                    break;
                case "*":
                    System.out.println();
                    System.out.printf("Result: %.2f%n", num1 * num2);
                    break;
                case "/":
                    System.out.println();
                    System.out.printf("Result: %.2f%n", num1 / num2);
                    break;
                default:
                    System.out.println("Invalid operator.");
                    break;
            }
            scanner.nextLine();
            System.out.printf("%n Run again [ENTER] | exit [X] : ");
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            String exitInput = scanner.nextLine();
            if (exitInput.equalsIgnoreCase("exit")) {
                run = false;
            }
        }
        scanner.close();
        System.out.printf("Calculator closed.%n");
    }
}