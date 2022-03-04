package ui;

import model.Computation;
import model.RiemmanSum;

import java.util.Scanner;

/*
    RiemmanSumApp houses all UI handling functionality including
    asking user for input, then pinging the RiemmanSum class for data
    about each RiemmanSum calculation to output onto standard io.

    NOTE:
    Part of the RiemmanSumApp constructor, runConsoleLoop(), displayCommandMenu(), and
    commandHandler borrow code from the CPSC 210 TellerApp found at
    https://github.students.cs.ubc.ca/CPSC210/TellerApp
 */

public class RiemmanSumApp {
    private static final String JSON_STORE = "./data/riemmanSumData.json";
    private RiemmanSum riSum;
    private Scanner consoleInput;

    private String mathFunctionType;
    private String mathFunction;
    private double intervalA;
    private double intervalB;
    private int numOfRectsN;
    private String sumType;
    private double currentResult;

    // EFFECTS: constructs Riemman Sum Application and initializes UI elements
    public RiemmanSumApp() {
        consoleInput = new Scanner(System.in);
        consoleInput.useDelimiter("\n");

        mathFunctionType = "";
        mathFunction = "";
        intervalA = 0.0;
        intervalB = 0.0;
        numOfRectsN = 1;
        sumType = "";
        currentResult = 0.0;

        runConsoleLoop();
    }

    // MODIFIES: this
    // EFFECTS: Receives user's input on necessary function and partition values for sum
    //          and begins UI loop
    private void runConsoleLoop() {
        printWelcomeScreen();
        inputNewRiemmanSum();
        riSum = new RiemmanSum(sumType, mathFunctionType, mathFunction, intervalA, intervalB, numOfRectsN);
        computeResult();

        boolean hasQuit = false;
        String newCommand;

        while (!hasQuit) {
            displayCommandMenu();
            newCommand = consoleInput.next().toLowerCase().trim();

            if (newCommand.equals("q")) {
                hasQuit = true;
            } else {
                commandHandler(newCommand);
            }
        }

        System.out.println("Exiting application...");
    }

    // MODIFIES: this
    // EFFECTS: Parses user-inputted commands to direct the program to the appropriate function/task
    private void commandHandler(String nextInput) {

        if (nextInput.equals("h")) {
            printOutComputationStats();
        } else if (nextInput.equals("r")) {
            recomputeAdjustedRiemmanSum();
        } else if (nextInput.equals("n")) {
            computeNewRiemmanSum();
        } else {
            System.out.println("Invalid input.");
        }
    }

    // MODIFIES: this
    // EFFECTS: Takes in new "n" and "Riemman Sum Type" values to compute a new sum,
    //          and compares it with the old result
    private void recomputeAdjustedRiemmanSum() {
        System.out.println("Input your new \"n\" value: ");
        int newValueN = Integer.parseInt(askForInput());

        System.out.println("Input your new Riemman Sum Type (Left, Right, or Midpoint): ");
        String newSumType = askForInput().toLowerCase();

        System.out.println("Old \"n\" value: " + numOfRectsN + ", Sum type: "
                + sumType + ", and Result: " + currentResult);

        double newResult = riSum.recomputeAdjustedSum(newValueN, newSumType);

        System.out.println("New \"n\" value: " + newValueN + ", Sum type: "
                + newSumType + ", and Result: " + newResult);

        numOfRectsN = newValueN;
        sumType = newSumType;
        currentResult = newResult;
    }

    // EFFECTS: Prints welcome instructions to the console
    private void printWelcomeScreen() {
        System.out.println("Hello, welcome to the Riemman Sum calculator!");
        System.out.println("This calculator serves as a tool for approximating integrals.\n");

        System.out.print("NOTE: functions of type sin(x), cos(x), tan(x), ln(x), log(x), and linear functions");
        System.out.print(" are only supported right now.\n");
        System.out.print("They also only accept an integer as a vertical scaling factor, ");
        System.out.println("so everything else will be ignored/not behave correctly at the moment.\n");
    }

    // MODIFIES: this
    // EFFECTS: Prompts user to input all the relevant data required for a new Riemman Sum
    private void inputNewRiemmanSum() {
        System.out.println("Input your function type (Trigonometric, Logarithmic, or Linear): ");
        mathFunctionType = askForInput().toLowerCase();

        System.out.println("Input your function: ");
        mathFunction = askForInput().toLowerCase();

        System.out.println("Input your \"a\" value: ");
        intervalA = Double.parseDouble(askForInput());

        System.out.println("Input your \"b\" value: ");
        intervalB = Double.parseDouble(askForInput());

        System.out.println("Input your \"n\" value: ");
        numOfRectsN = Integer.parseInt(askForInput());

        System.out.println("Input Riemman Sum type (Left, Right, or Midpoint): ");
        sumType = askForInput().toLowerCase();
    }

    // MODIFIES: this
    // EFFECTS: Prompts user for their input, and keeps retrying until a valid input is given
    private String askForInput() {
        String userInput;

        do {
            userInput = consoleInput.next();
        } while (userInput.isEmpty());

        return userInput.trim();
    }

    // MODIFIES: this
    // EFFECTS: Computes the Riemman Sum and prints it to console
    private void computeResult() {
        double result = riSum.computeRiemmanSum();
        System.out.println("\nYour Riemman Sum result is: " + result);
        currentResult = result;
    }

    // MODIFIES: this
    // EFFECTS: Takes in input for a new Riemman Sum, computes its result, and relays it to the console
    private void computeNewRiemmanSum() {
        inputNewRiemmanSum();
        riSum.addNewRiemmanSum(sumType, mathFunctionType, mathFunction, intervalA, intervalB, numOfRectsN);
        computeResult();
    }

    // EFFECTS: Prints all available commands to the console
    private void displayCommandMenu() {
        System.out.println("\nInput \"h\" if you want to see a history of your computations.");
        System.out.println("Input \"r\" if you want to readjust your \"n\" value and/or sum type for current sum.");
        System.out.println("Input \"n\" if you want to compute a new Riemman Sum.");
        System.out.println("Input \"q\" if you want to quit.");
    }

    // EFFECTS: prints list of all computations made so far and presents it in a nice format
    private void printOutComputationStats() {
        System.out.print('\n');
        System.out.println("---------");

        for (Computation c : riSum.getComputationHistory()) {
            for (String s : c.produceStats()) {
                System.out.println(s);
            }
            System.out.println("---------");
        }
        System.out.print("\n");
    }

}
