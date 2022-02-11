package model;

import java.util.ArrayList;
import java.util.List;

/*
    Riemman Sum class that encapsulates core
    approximation functionality such as computing left, right
    and midpoint Riemman sums, in addition to storing history
    of computations.
 */
public class RiemmanSum {
    private int compId = 1;
    private List<Computation> computationHistory;
    private Computation currentComputation;
    private MathFunction currentFunction;

    // REQUIRES: riemmanSumType is one of LEFT, RIGHT, OR MIDPOINT,
    //           mathFuncType is one of TRIGONOMETRIC, LOGARITHMIC, LINEAR,
    //           mathFunction abides by the constraints outlined in the README.md
    //           n > 0
    // EFFECTS: Constructs Riemman Sum with user-inputted partition and n values.
    public RiemmanSum(String riemmanSumType, String mathFuncType, String mathFunction, double a, double b, int n) {
        this.computationHistory = new ArrayList<>();
        this.currentComputation = new Computation(compId++, riemmanSumType, mathFuncType, mathFunction, a, b, n);
        this.currentFunction = new MathFunction(mathFuncType, mathFunction);
    }

    // MODIFIES: this
    // EFFECTS: Compute the Riemman Sum result for given riemman sum type (left, right, or mid)
    public double computeRiemmanSum() {
        if (currentComputation.getRiemmanSumTypeString().equals("left")) {
            return computeLeftSum();
        } else if (currentComputation.getRiemmanSumTypeString().equals("right")) {
            return computeRightSum();
        } else {
            return computeMidSum();
        }
    }

    // REQUIRES: riemmanSumType is one of LEFT, RIGHT, OR MIDPOINT,
    //           mathFuncType is one of TRIGONOMETRIC, LOGARITHMIC, LINEAR,
    //           mathFunction abides by the constraints outlined in the README.md
    //           n > 0
    // MODIFIES: this
    // EFFECTS: Creates new Computation and MathFunction objects to do a new Riemman Sum computation
    public void addNewRiemmanSum(String riemmanSumType, String mathFuncType, String mathFunction,
                                 double a, double b, int n) {
        currentComputation = new Computation(compId++, riemmanSumType, mathFuncType, mathFunction, a, b, n);
        this.currentFunction = new MathFunction(mathFuncType, mathFunction);
    }

    // REQUIRES: n > 0
    // MODIFIES: this
    // EFFECTS: Adjusts only the N and Riemman Sum Type fields and recomputes the Riemman Sum
    public double recomputeAdjustedSum(int newN, String newSumType) {
        addNewRiemmanSum(newSumType, currentComputation.getComputationFunctionType(),
                currentFunction.getFunction(), getIntervalA(), getIntervalB(), newN);
        return computeRiemmanSum();
    }

    // MODIFIES: this
    // EFFECTS: Computes the result of a mathematical function computation for a given x.
    private double computeFunctionAtX(double x) {
        return currentFunction.applyComputation(x);
    }

    // MODIFIES: this
    // EFFECTS: Computes right Riemman sum and adds it to computationHistory
    private double computeRightSum() {
        double a = currentComputation.getIntervalA();
        double dx = currentComputation.getDeltaX();
        int n = currentComputation.getNumOfRectanglesN();
        double sum = 0.0;

        for (int i = 1; i <= n; ++i) {
            sum += this.computeFunctionAtX(a + ((double)i * dx));
        }

        sum *= dx;
        currentComputation.setComputationResult(sum);
        computationHistory.add(currentComputation);
        return sum;
    }

    // MODIFIES: this
    // EFFECTS: Computes left Riemman sum and adds it to computationHistory
    private double computeLeftSum() {
        double a = currentComputation.getIntervalA();
        double dx = currentComputation.getDeltaX();
        int n = currentComputation.getNumOfRectanglesN();
        double sum = 0.0;

        for (int i = 1; i <= n; ++i) {
            sum += this.computeFunctionAtX(a + ((double)(i - 1) * dx));
        }

        sum *= dx;

        currentComputation.setComputationResult(sum);
        computationHistory.add(currentComputation);
        return sum;
    }

    // MODIFIES: this
    // EFFECTS: Computes mid Riemman sum and adds it to computationHistory
    private double computeMidSum() {
        double a = currentComputation.getIntervalA();
        double dx = currentComputation.getDeltaX();
        int n = currentComputation.getNumOfRectanglesN();
        double sum = 0.0;

        for (int i = 1; i <= n; ++i) {
            sum += this.computeFunctionAtX(a + ((double)(i - 0.5f) * dx));
        }

        sum *= dx;
        currentComputation.setComputationResult(sum);
        computationHistory.add(currentComputation);
        return sum;
    }

    // getters
    public String getFunction() {
        return currentComputation.getComputationFunction();
    }

    public double getIntervalB() {
        return currentComputation.getIntervalB();
    }

    public double getIntervalA() {
        return currentComputation.getIntervalA();
    }

    public int getNumOfRectangles() {
        return currentComputation.getNumOfRectanglesN();
    }

    public double getDeltaX() {
        return currentComputation.getDeltaX();
    }

    public List<Computation> getComputationHistory() {
        return computationHistory;
    }

    public int getComputationHistorySize() {
        return computationHistory.size();
    }

}