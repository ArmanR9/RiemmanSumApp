package ui;

import model.Computation;
import model.RiemmanSum;
import org.json.JSONException;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/*
    RiemmanSumApp houses all UI handling functionality including
    asking user for input, then pinging the RiemmanSum class for data
    about each RiemmanSum calculation to output onto standard io.

    NOTE:
    Part of the RiemmanSumApp constructor, runConsoleLoop(), displayCommandMenu(), and
    commandHandler borrow code from the CPSC 210 TellerApp found at
    https://github.students.cs.ubc.ca/CPSC210/TellerApp

    The JSON writing and reading functionality is largely modelled off of
    https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */

public class RiemmanSumApp {
    private static final String JSON_STORE = "./data/riemmanSumData.json";
    private RiemmanSum riSum;
    private Scanner consoleInput;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private boolean hasSaved;

    private String mathFunctionType;
    private String mathFunction;
    private double intervalA;
    private double intervalB;
    private int numOfRectsN;
    private String sumType;
    private double currentResult;

    // EFFECTS: constructs Riemman Sum Application, initializes file I/O elements,
    //          and initializes UI elements
    public RiemmanSumApp() {
        consoleInput = new Scanner(System.in);
        consoleInput.useDelimiter("\n");

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        hasSaved = false;

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
    // EFFECTS: Loads Riemman Sum data from file or receives user's input on necessary function and partition values
    //          for sum and then begins UI loop
    private void runConsoleLoop() {
        printWelcomeScreen();

        if (!askToLoadFromFile()) {
            inputNewRiemmanSum();
            riSum = new RiemmanSum(sumType, mathFunctionType, mathFunction, intervalA, intervalB, numOfRectsN);
            computeResult();
        }

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

        if (!hasSaved) {
            askToSaveToFile();
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
            hasSaved = false;
        } else if (nextInput.equals("n")) {
            computeNewRiemmanSum();
            hasSaved = false;
        } else if (nextInput.equals("l")) {
            loadRiemmanSum();
        } else if (nextInput.equals("s")) {
            hasSaved = true;
            saveRiemmanSum();
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
    // EFFECTS: prompts user to load old Riemman Sum from file before getting into the application
    //          and returns true if they select yes
    private boolean askToLoadFromFile() {
        System.out.println("Would you like to load your previous Riemman Sum computation history? [y/n]");
        String input = askForInput().toLowerCase();

        while (!input.equals("n") && !input.equals("y")) {
            System.out.println("Invalid input, try typing just \"y\" or \"n\"");
            input = askForInput().toLowerCase();
        }

        if (input.equals("y")) {
            loadRiemmanSum();
            return true;
        } else {
            return false;
        }
    }

    // EFFECTS: prompts user to save current Riemman Sum to file before quitting
    private void askToSaveToFile() {
        System.out.print("You haven't saved your current Riemman Sums yet? Would you like to save them to file? ");
        System.out.println("[y/n]");

        String input = askForInput().toLowerCase();

        while (!input.equals("n") && !input.equals("y")) {
            System.out.println("Invalid input, try typing just \"y\" or \"n\"");
            input = askForInput().toLowerCase();
        }

        if (input.equals("y")) {
            saveRiemmanSum();
        }
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
        System.out.println("Input \"l\" if you want to load previous Riemman Sum(s) from file.");
        System.out.println("Input \"s\" if you want to save the current Riemman Sum(s) to file.");
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

    // EFFECTS: saves the Riemman Sum to file
    private void saveRiemmanSum() {
        try {
            jsonWriter.open();
            jsonWriter.write(riSum);
            jsonWriter.close();
            System.out.println("Saved current Riemman Sum to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads Riemman Sum from file
    private void loadRiemmanSum() {
        try {
            riSum = jsonReader.read();

            mathFunctionType = riSum.getFunctionType();
            mathFunction = riSum.getFunction();
            intervalA = riSum.getIntervalA();
            intervalB = riSum.getIntervalB();
            numOfRectsN = riSum.getNumOfRectangles();
            sumType = riSum.getRiemmanSumType();

            System.out.println("Loaded Riemman Sum from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        } catch (JSONException e) {
            System.out.println(JSON_STORE + " is empty or has invalid contents");
        }
    }

}
