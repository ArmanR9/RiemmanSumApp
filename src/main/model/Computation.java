package model;

import java.util.ArrayList;
import java.util.List;

/*
    Computation class that houses all statistics about
    each Riemman Sum, including:
     - The Riemman Sum number
     - Riemman Sum type (left, right, mid)
     - Riemman Sum function
     - A interval value
     - B interval value
     - Number of rectangles
     and functionality to output that data in a nice
     format.
 */
public class Computation {

    enum SumType {
        LEFT,
        RIGHT,
        MIDPOINT,
    }

    private int computationNumber;
    private SumType riemmanSumType;
    private MathFunction computationFunction;
    private double computationResult;
    private double intervalB;
    private double intervalA;
    private int numOfRectanglesN;
    private double deltaX;


    // EFFECTS: Constructs computation object with relevant data about the Riemman Sum
    public Computation(int compId, String sumType, String funcType, String function, double a, double b, int n) {
        this.computationNumber = compId;
        this.riemmanSumType = parseSumType(sumType);
        this.computationFunction = new MathFunction(funcType, function);
        this.computationResult = 0.0;
        this.intervalA = a;
        this.intervalB = b;
        this.numOfRectanglesN = n;
        this.deltaX = (b - a) / n;
    }


    // MODIFIES: this
    // EFFECTS: Parses string and converts it into correct sum type (left, right, or midpoint)
    private SumType parseSumType(String type) {
        type = type.toLowerCase();

        if (type.contains("left")) {
            riemmanSumType = SumType.LEFT;
        } else if (type.contains("right")) {
            riemmanSumType = SumType.RIGHT;
        } else if (type.contains("mid")) {
            riemmanSumType = SumType.MIDPOINT;
        } else {
            riemmanSumType = null;
        }

        return riemmanSumType;
    }


    // REQUIRES: riemmanSumType != null
    // EFFECTS: Wraps Sum's statistics (including its interval, function, partition size, etc.)
    //          into a list of strings
    public List<String> produceStats() {
        List<String> statsArray = new ArrayList<>();
        statsArray.add("Computation number: " + this.computationNumber);
        statsArray.add("Function used: " + this.computationFunction.getFunction());

        if (riemmanSumType.equals(SumType.LEFT)) {
            statsArray.add("Riemman Sum Type: Left Sum");
        } else if (riemmanSumType.equals(SumType.RIGHT)) {
            statsArray.add("Riemman Sum Type: Right Sum");
        } else {
            statsArray.add("Riemman Sum Type: Midpoint Sum");
        }

        statsArray.add("Interval: [" + this.intervalA + ", " + this.intervalB + "]");
        statsArray.add("Number of rectangles: " + this.numOfRectanglesN);
        statsArray.add("Partition size: " + this.deltaX);
        return statsArray;
    }

    // MODIFIES: this
    // EFFECTS: sets this.computationResult to res
    public void setComputationResult(double res) {
        this.computationResult = res;
    }

    // MODIFIES: this
    // EFFECTS: sets this.riemmanSumType to newType
    public void setRiemmanSumType(String newType) {
        this.riemmanSumType = parseSumType(newType);
    }

    // REQUIRES: n > 0
    // MODIFIES: this
    // EFFECTS: adjusts the number of rectangles of current computation
    public void setNumOfRectanglesN(int n) {
        numOfRectanglesN = n;
    }

    // getters
    public int getComputationNumber() {
        return computationNumber;
    }

    public SumType getRiemmanSumType() {
        return riemmanSumType;
    }

    public double getComputationResult() {
        return computationResult;
    }

    public String getRiemmanSumTypeString() {
        return riemmanSumType.name().toLowerCase();
    }

    public String getComputationFunction() {
        return computationFunction.getFunction();
    }

    public String getComputationFunctionType() {
        return computationFunction.getFunctionType().name().toLowerCase();
    }

    public double getIntervalB() {
        return intervalB;
    }

    public double getIntervalA() {
        return intervalA;
    }

    public int getNumOfRectanglesN() {
        return numOfRectanglesN;
    }

    public double getDeltaX() {
        return deltaX;
    }



}
