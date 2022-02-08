package ui;

import model.RiemmanSum;

public class RiemmanSumApp {
    private RiemmanSum sum;

    // EFFECTS: constructs Riemman sum application.
    public RiemmanSumApp(){
        // stub
    }

    // EFFECTS: Receives user's input on necessary function and partition values for sum
    public void getUserInput(){
        // stub
    }

    // EFFECTS: prints list of all computations made so far
    void printValues() {
        for (Double r : sum.getComputationHistory()) {
            System.out.print(r + " ");
        }

        System.out.print("\n");
    }

    // WILL HANDLE I/O
}
