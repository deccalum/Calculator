package se.lexicon;

import java.util.Scanner;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Main {

    public class FunctionEvaluator {

        public static double evaluateMathString(String expressionString) {
            Expression expression = new ExpressionBuilder(expressionString).build();
            return expression.evaluate();
        }
    }
    public static void main(String[] args) {
        boolean run = true;
        Scanner scanner = new Scanner(System.in);
        
        while (run) {
            System.out.printf("Simple Calculator [1] | Complex Calculator [2] | Exit [X]%n"); 
            
            String choice = scanner.next();

            if (choice.equals("1")) {
                simpleCalculator(scanner);
            } else if (choice.equals("2")) {
                complexCalculator(scanner);
            } else if (choice.equalsIgnoreCase("X")) {
                run = false;
            }
            System.out.println();
        }
        scanner.close();
        System.out.printf("Closed%n");
    }

    private static void simpleCalculator(Scanner scanner) {
        boolean continueCalculation = true;

        while (continueCalculation) {
        System.out.printf("Simple Calculator Selected.%n");

                System.out.printf("Enter your first number: ");
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
            }
            scanner.nextLine();
            System.out.print("Any key [REPEAT] | Exit [X]: ");
            String continueChoice = scanner.nextLine();
            if (continueChoice.equalsIgnoreCase("X")) {
                continueCalculation = false;
            }
        }
    }
    
    private static void complexCalculator(Scanner scanner) {
        boolean continueCalculation = true;
        while (continueCalculation) {
        System.out.printf("Complex Calculator Selected.%n");
        System.out.printf("Enter any legit expression, accepts +, -, *, /, ^ and parentheses:%n");
        scanner.nextLine(); 
        String expression = scanner.nextLine();

        double result = FunctionEvaluator.evaluateMathString(expression);;
        System.out.printf("Result: %.2f%n", result);
            System.out.print("Any key [REPEAT] | Exit [X]: ");
            String continueChoice = scanner.nextLine();
            if (continueChoice.equalsIgnoreCase("X")) {
                continueCalculation = false;
            }
        }
    }
}