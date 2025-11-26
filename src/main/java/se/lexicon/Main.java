package se.lexicon;

import java.util.Scanner;

// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        boolean run = true;
        Scanner scanner = new Scanner(System.in);
        while (run) {
            System.out.printf("Calculator%n");
            System.out.printf("Enter calculus: ");

            scanner.nextLine();

            int choice = scanner.nextInt();

            if (choice == 0) run = false;
        }
        System.out.printf("Exiting...");
        //Basic calculation
        // add. sub. mult. div.

        // need to read numbers with nextDouble()?
        // requires input
        // read only nuumbers or operators
        // able to perform multiple operations and exit

        // handle/print errors
        // advanced; square, eqvations, constants, physics etc.
        // Simple GUI. (Swing, JAVAFX)
        }
    }