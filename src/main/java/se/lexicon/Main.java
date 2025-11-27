package se.lexicon;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Main {

    private static final Map<String, Double> VARIABLES = new LinkedHashMap<>();
    private static final Map<String, String> VARIABLE_EXPRESSIONS = new LinkedHashMap<>();
    private static final List<String> HISTORY = new ArrayList<>();
    private static double ANS = 0.0;
    private static final Pattern ASSIGN = Pattern.compile("^\\s*([A-Za-z][A-Za-z0-9_]*)\\s*=\\s*(.+)\\s*$");
    
    /*
    ^\\s* - Start, optional whitespace
    ([A-Za-z][A-Za-z0-9_]*) - Group 1: Variable name (letter, then letters/digits/underscore)
    \\s*=\\s* - Equals sign with optional spaces
    (.+) - Group 2: Expression (anything)
    \\s*$ - Optional whitespace, end 
    */


    static {
        VARIABLES.put("ans", 0.0);
    }

    public static class FunctionEvaluator {
        public static double evaluateMathString(String expressionString) {
            ExpressionBuilder builder = new ExpressionBuilder(expressionString);
            if (VARIABLES.size() > 0) {
                builder = builder.variables(VARIABLES.keySet()); // Tells exp4j about variable names
            }
            Expression expression = builder.build();
            for (Map.Entry<String, Double> entry : VARIABLES.entrySet()) {
                expression.setVariable(entry.getKey(), entry.getValue()); // Replaces variable names with their actual values
            }
            return expression.evaluate();
        }
    }

    public static void main(String[] args) {
        boolean run = true;
        Scanner scanner = new Scanner(System.in);
        
        while (run) {
            System.out.printf("%n");
            System.out.printf("'''''CALCULATOR MENU'''''%n");
            System.out.printf("[1] Simple Calculator%n");
            System.out.printf("[2] Complex Calculator%n");
            System.out.printf("[V] View Variables%n");
            System.out.printf("[H] View History%n");
            System.out.printf("[X] Exit%n");
            System.out.printf("%n> ");
            String choice = scanner.next();

            if (choice.equals("1")) {
                simpleCalculator(scanner);
            } else if (choice.equals("2")) {
                complexCalculator(scanner);
            } else if (choice.equalsIgnoreCase("V")) {
                printVariables();
            } else if (choice.equalsIgnoreCase("H")) {
                printHistory();
            } else if (choice.equalsIgnoreCase("X")) {
                run = false;
            } else {
                System.out.printf("%nError: Invalid option. Please try again.%n");
            }
        }
        scanner.close();
        System.out.printf("%n'''''Calculator Closed'''''%n%n");
    }

    private static void simpleCalculator(Scanner scanner) {
        boolean continueCalculation = true;

            while (continueCalculation) {
                System.out.printf("%n");
                System.out.printf("'''''SIMPLE CALCULATOR'''''%n");
                System.out.printf("Enter first number or variable:%n> ");
                String input1 = scanner.next();
                Double num1 = parseOperand(input1);
                if (num1 == null) {
                    System.out.printf("%nError: Unknown variable '%s'%n", input1);
                    scanner.nextLine();
                    continue;
                }

                System.out.printf("%nEnter operator:%n");
                System.out.printf("  [+] or [1] Add%n");
                System.out.printf("  [-] or [2] Subtract%n");
                System.out.printf("  [*] or [3] Multiply%n");
                System.out.printf("  [/] or [4] Divide%n");
                System.out.printf("%n> ");
                String operator = scanner.next();

                System.out.printf("%nEnter second number or variable:%n> ");
                String input2 = scanner.next();
                Double num2 = parseOperand(input2);  
                if (num2 == null) {
                    System.out.printf("%nError: Unknown variable '%s'%n", input2);
                    scanner.nextLine();
                    continue;
                }

                Double result = null;
                String displayOperator = operator;
                boolean validOperation = true;
                
                switch (operator) {
                    case "+":
                    case "1":
                        result = num1 + num2;
                        displayOperator = "+";
                        break;
                    case "-":
                    case "2":
                        result = num1 - num2;
                        displayOperator = "-";
                        break;
                    case "*":
                    case "3":
                        result = num1 * num2;
                        displayOperator = "*";
                        break;
                    case "/":
                    case "4":
                        if (num2 == 0) {
                            System.out.printf("%nError: Division by zero is not allowed.%n");
                            validOperation = false;
                        } else {
                            result = num1 / num2;
                            displayOperator = "/";
                        }
                        break;
                    default:
                        System.out.printf("%nError: Invalid operator.%n");
                        validOperation = false;
                        break;
                }

                if (!validOperation) {
                    scanner.nextLine();
                    continue;
                }

                if (result != null) {
                    System.out.printf("%n");
                    System.out.printf("  %.2f %s %.2f = %.2f%n", num1, displayOperator, num2, result);
                    System.out.printf("  Result: %.2f%n", result);
                    saveResult(String.format("%.2f %s %.2f", num1, displayOperator, num2), result);
                    System.out.printf("%n");
                    assignPrompt(scanner, result);
                }

            scanner.nextLine();
            boolean menuActive = true;
            while (menuActive) {
                System.out.printf("%n[R] Repeat | [S] Store | [V] Variables | [H] History | [M] Menu | [X] Exit%n> ");
                String choice = scanner.nextLine().trim().toUpperCase();
                switch (choice) {
                    case "R":
                        menuActive = false; // Exits menu
                        break;
                    case "S":
                        assignPrompt(scanner, result);
                        break;
                    case "V":
                        printVariables();
                        break;
                    case "H":
                        printHistory();
                        break;
                    case "M":
                        continueCalculation = false;
                        menuActive = false;
                        break;
                    case "X":
                        System.out.printf("Exiting Calculator.%n");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
            }
        }
    }
    
    private static void complexCalculator(Scanner scanner) {
        boolean continueCalculation = true;
        scanner.nextLine();

        while (continueCalculation) {
            System.out.printf("%n");
            System.out.printf("'''''COMPLEX CALCULATOR'''''%n");
            System.out.printf("Operators: + - * / ^ ( )%n");
            System.out.printf("Commands: 'variables' | 'history'%n");
            System.out.printf("%n> ");
            String line = scanner.nextLine().trim();

            if (line.isEmpty()) {
                System.out.printf("%nError: Expression cannot be empty.%n");
                continue;
            }
            if (line.equalsIgnoreCase("variables")) {
                printVariables();
                continue;
            }
            if (line.equalsIgnoreCase("history")) {
                printHistory();
                continue;
            }


            double result = 0.0;             //Declare 'result' before the if-else so it's accessible outside
            boolean calculationSuccess = false; // Flag to track if calculation was successful

            Matcher m = ASSIGN.matcher(line);
            if (m.matches()) {
                String variableName = m.group(1); // Extracts variable name
                String expression = m.group(2); // Extracts expression

                if (expression.trim().isEmpty()) {
                    System.out.printf("%nError: Expression cannot be empty.%n");
                    continue;
                }

                Double evaluationResult = safeEvaluate(expression);
                if (evaluationResult == null) {
                    continue; // Error message already printed in safeEvaluate
                }

                result = evaluationResult;
                saveResult(variableName + " = " + expression, result);
                VARIABLES.put(variableName, result);
                VARIABLE_EXPRESSIONS.put(variableName, expression);
                System.out.printf("%n  Assigned: %s = %.2f%n", variableName, result);
                calculationSuccess = true;
            }

            else {
                Double evaluationResult = safeEvaluate(line);  // Evaluate the expression directly
                if (evaluationResult == null) {
                    continue; // Error message already printed in safeEvaluate
                }

                result = evaluationResult;

                String displayExpression = formatWithSuperscripts(line);

                saveResult(line, result);
                System.out.printf("%n  %s = %.2f%n", displayExpression, result);
                calculationSuccess = true;
            }

            if (calculationSuccess) {
            boolean menuActive = true;
            while (menuActive) {
                System.out.printf("%n[R] Repeat | [S] Store | [V] Variables | [H] History | [M] Menu | [X] Exit%n> ");
                String choice = scanner.nextLine().trim().toUpperCase();
                switch (choice) {
                    case "R":
                        menuActive = false; // Exits menu
                        break;
                    case "S":
                        assignPrompt(scanner, result);
                        break;
                    case "V":
                        printVariables();
                        break;
                    case "H":
                        printHistory();
                        break;
                    case "M":
                        continueCalculation = false;
                        menuActive = false;
                        break;
                    case "X":
                        System.out.printf("%n'''''Exiting Calculator'''''%n");
                        System.exit(0);
                        break;
                    default:
                        System.out.printf("%nError: Invalid choice. Please try again.%n");
                        break;
                }
            }
        }
    }
}

private static Double safeEvaluate(String expression) {
    try {
            return FunctionEvaluator.evaluateMathString(expression);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.out.println("Error: Invalid expression or unknown variable.");
            return null;
        }
    }

    private static Double parseOperand(String token) {
        if (VARIABLES.containsKey(token)) {
            return VARIABLES.get(token);
        }
        try {
            return Double.parseDouble(token);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static void printVariables() {
        System.out.printf("%n");
        System.out.printf("'''''STORED VARIABLES'''''%n");
        if (VARIABLES.isEmpty()) {
            System.out.printf("  (none)%n");
        } else {
            for (Map.Entry<String, Double> entry : VARIABLES.entrySet()) {
                System.out.printf("  %s = %.2f%n", entry.getKey(), entry.getValue());
            }
        }
        System.out.printf("%n");
    }

    private static void printHistory() {
        System.out.printf("%n");
        System.out.printf("'''''CALCULATION HISTORY'''''%n");
        if (HISTORY.isEmpty()) {
            System.out.printf("  (empty)%n");
        } else {
            for (int i = 0; i < HISTORY.size(); i++) {
                System.out.printf("  %d) %s%n", i + 1, HISTORY.get(i));
            }
        }
        System.out.printf("%n");
    }

    private static void saveResult(String expression, double result) {
        String historyEntry = String.format("%s = %.2f", expression, result);
        HISTORY.add(historyEntry);
        VARIABLES.put("ans", result);
        ANS = result;
    }

    private static void assignPrompt(Scanner scanner, double result) {
        System.out.printf("Assign to variable (or press Enter to skip):%n> ");
        String variableName = scanner.nextLine().trim();
        if (variableName.isEmpty()) return;
        if (!variableName.matches("[A-Za-z][A-Za-z0-9_]*")) {
            System.out.printf("%nError: Invalid variable name (must start with letter).%n");
            return;
        }
        VARIABLES.put(variableName, result);
        System.out.printf("%n  Assigned: %s = %.2f%n", variableName, result);
    }

    private static String formatWithSuperscripts(String expression) {
        return expression
            .replace("^0", "⁰")
            .replace("^1", "¹")
            .replace("^2", "²")
            .replace("^3", "³")
            .replace("^4", "⁴")
            .replace("^5", "⁵")
            .replace("^6", "⁶")
            .replace("^7", "⁷")
            .replace("^8", "⁸")
            .replace("^9", "⁹");
    }

    private static String expandVariables(String expression) {
        String result = expression;
        for (Map.Entry<String, String> entry : VARIABLE_EXPRESSIONS.entrySet()) {
            String varName = entry.getKey();
            String varExpr = entry.getValue();
            // Replace variable with its expression in parentheses
            result = result.replaceAll("\\b" + varName + "\\b", "(" + varExpr + ")");
        }
        return result;
    }

}


// SUGGESTIONS:
// 1. Add ability to delete variables (e.g., "del a" or "delete a")
// 2. Add ability to clear all variables or history (e.g., "clear vars", "clear history")
// 3. Support negative numbers in simple calculator (currently -5 might be interpreted incorrectly)
// 5. Save/load calculator state to file (variables + history persistence)
// 6. Add more mathematical functions: sin, cos, tan, sqrt, log, etc. (exp4j supports these)
// 6.1 Add constants like pi and e
// 7. Show variable expressions when viewing variables (not just values)
// 8. Add color coding for output (errors in red, results in green) using ANSI codes
// 9. Add unit tests for core functionality
// 10. Add input validation to prevent buffer overflow on very long expressions
// 11. Consider adding scientific notation support for very large/small numbers
// 12. Add keyboard shortcuts or aliases (e.g., "?" for help, "q" for quit)