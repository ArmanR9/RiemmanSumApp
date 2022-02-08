package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RiemmanSum {
    private List<Computation> computationStats;
    private List<Double> computationHistory;
    private double intervalB;
    private double intervalA;
    private int numOfRectanglesN;
    private double deltaX;
    // Test function: 4 * sqrt(1 - x^2)

    // EFFECTS: Constructs Riemman Sum with user-inputted partition and n values.
    public RiemmanSum(double a, double b, int n) {
        this.computationHistory = new ArrayList<>();
        this.numOfRectanglesN = n;
        this.intervalB = b;
        this.intervalA = a;
        this.deltaX = (b - a) / n;
    }


    // MODIFIES: this
    // EFFECTS: Computes right Riemman sum and adds it to computationHistory
    public double computeRightSum() {

        double sum = 0.0;

        for (int i = 1; i <= numOfRectanglesN; ++i) {
            sum += 4 * Math.sqrt(1 - Math.pow(((double)i / numOfRectanglesN), 2));

            if (Double.isNaN(sum)) {
                throw new ArithmeticException("Interval leads to negative square root -- cannot compute.");
            }
        }

        sum *= deltaX;
        computationHistory.add(sum);
        return sum;
    }

    // MODIFIES: this
    // EFFECTS: Computes left Riemman sum and adds it to computationHistory
    public double computeLeftSum() {

        double sum = 0.0;

        for (int i = 1; i <= numOfRectanglesN; ++i) {
            sum += 4 * Math.sqrt(1 - Math.pow(intervalA + ((double) (i - 1) * deltaX), 2));

            if (Double.isNaN(sum)) {
                throw new ArithmeticException("Interval leads to negative square root -- cannot compute.");
            }

        }

        sum *= deltaX;
        computationHistory.add(sum);
        return sum;
    }

    // MODIFIES: this
    // EFFECTS: Computes mid Riemman sum and adds it to computationHistory
    public double computeMidSum() {

        double sum = 0.0;

        for (int i = 1; i <= numOfRectanglesN; ++i) {
            sum += 4 * Math.sqrt(1 - Math.pow(intervalA + ((double)i - 0.5) * deltaX, 2));

            if (Double.isNaN(sum)) {
                throw new ArithmeticException("Interval leads to negative square root -- cannot compute.");
            }
        }

        sum *= deltaX;
        computationHistory.add(sum);
        return sum;
    }

    // MODIFIES: this
    // EFFECTS: Computes trapezoidal Riemman sum and adds it to computationHistory
    public double computeTrapezoidalSum() {

        double sum = 0.0;
        sum += 4 * Math.sqrt(1 - Math.pow(intervalA, 2));

        for (int i = 1; i < numOfRectanglesN; ++i) {
            sum += 2 * (4 * Math.sqrt(1 - Math.pow(intervalA + ((double)i * deltaX), 2)));
        }

        sum += 4 * Math.sqrt(1 - Math.pow(intervalB, 2));

        sum *= (deltaX * 0.5);
        computationHistory.add(sum);
        return sum;
    }

    // MODIFIES: this
    // EFFECTS: Computes Simpson's rule Riemman sum and adds it to computationHistory
    double computeSimpsonsRule() {

        double sum = 0.0;
        sum += 4 * Math.sqrt(1 - Math.pow(intervalA, 2));

        for (int i = 1; i < numOfRectanglesN; ++i) {
            if ((i % 2) != 0) {
                sum += 4 * (4 * Math.sqrt(1 - Math.pow(intervalA + ((double) i * deltaX), 2)));
            } else {
                sum += 2 * (4 * Math.sqrt(1 - Math.pow(intervalA + ((double) i * deltaX), 2)));
            }

            if (Double.isNaN(sum)) {
                throw new ArithmeticException("Interval leads to negative square root -- cannot compute.");
            }
        }

        sum += 4 * Math.sqrt(1 - Math.pow(intervalB, 2));

        sum *= (deltaX * (1.0 / 3.0));
        computationHistory.add(sum);
        return sum;
    }

    // EFFECTS: adjusts N values
    public void setNumOfRectanglesN(int n) {
        numOfRectanglesN = n;
    }


    // getters
    public double getIntervalB() {
        return intervalB;
    }

    public double getIntervalA() {
        return intervalA;
    }

    public int getNumOfRectangles() {
        return numOfRectanglesN;
    }

    public double getDeltaX() {
        return deltaX;
    }

    public List<Double> getComputationHistory() {
        return computationHistory;
    }

    public int getComputationHistorySize() {
        return computationHistory.size();
    }


}
