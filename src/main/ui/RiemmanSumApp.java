package ui;

import model.Computation;
import model.RiemmanSum;
import java.util.Scanner;

public class RiemmanSumApp {
    private RiemmanSum riSum;
    private Scanner input;

    // EFFECTS: constructs Riemman sum application and initializes UI
    public RiemmanSumApp() {
        runConsoleLoop();
    }

    // EFFECTS: Receives user's input on necessary function and partition values for sum
    public void runConsoleLoop() {
        riSum = new RiemmanSum("left", "trigonometric",
                "sin(x)", 0, Math.PI, 10);
        riSum.computeRiemmanSum();
        riSum.recomputeAdjustedSum(5, "right");

        printOutComputationStats();
    }

    // EFFECTS: prints list of all computations made so far
    void printOutComputationStats() {
        for (Computation c : riSum.getComputationHistory()) {
            for (String s : c.produceStats()) {
                System.out.println(s);
            }
            System.out.println("---------");
        }
        System.out.print("\n");
    }

    // WILL HANDLE I/O
}
