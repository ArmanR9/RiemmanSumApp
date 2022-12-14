package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

/*
    Riemman Sum class that encapsulates core
    approximation functionality such as computing left, right
    and midpoint Riemman sums, in addition to storing history
    of computations.
 */
public class RiemmanSum implements Writable {
    private int compId;
    private List<Computation> computationHistory;
    private Computation currentComputation;
    private MathFunction currentFunction;

    // REQUIRES: riemmanSumType is one of LEFT, RIGHT, OR MIDPOINT,
    //           mathFuncType is one of TRIGONOMETRIC, LOGARITHMIC, LINEAR,
    //           mathFunction abides by the constraints outlined in the README.md
    //           n > 0
    // EFFECTS: Constructs Riemman Sum with user-inputted related function, interval, and partition values
    public RiemmanSum(String riemmanSumType, String mathFuncType, String mathFunction, double a, double b, int n) {
        this.compId = 1;
        this.computationHistory = new ArrayList<>();
        this.currentComputation = new Computation(compId++, riemmanSumType, mathFuncType, mathFunction, a, b, n);
        this.currentFunction = new MathFunction(mathFuncType, mathFunction);
    }

    // EFFECTS: Constructs Riemman Sum from JSON file generated data.
    public RiemmanSum(int currentId, Computation currentComputationObj, MathFunction currentFunctionObj,
                      List<Computation> compHistoryList) {
        this.compId = currentId;
        this.currentComputation = currentComputationObj;
        this.currentFunction = currentFunctionObj;
        this.computationHistory = compHistoryList;
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
        compId = computationHistory.size() + 1;
        currentComputation = new Computation(compId, riemmanSumType, mathFuncType, mathFunction, a, b, n);
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
        int curCompId;

        for (int i = 1; i <= n; ++i) {
            sum += this.computeFunctionAtX(a + ((double)i * dx));
        }

        sum *= dx;
        currentComputation.setComputationResult(sum);
        computationHistory.add(currentComputation);
        curCompId = getComputationHistorySize();
        String eventDesc = "Added Computation #" + curCompId
                + " of name: " + currentFunction.getFunction()
                + " and type: right sum to Riemman Sum Computation history.";
        EventLog.getInstance().logEvent(new Event(eventDesc));
        return sum;
    }

    // MODIFIES: this
    // EFFECTS: Computes left Riemman sum and adds it to computationHistory
    private double computeLeftSum() {
        double a = currentComputation.getIntervalA();
        double dx = currentComputation.getDeltaX();
        int n = currentComputation.getNumOfRectanglesN();
        double sum = 0.0;
        int curCompId;

        for (int i = 1; i <= n; ++i) {
            sum += this.computeFunctionAtX(a + ((double)(i - 1) * dx));
        }

        sum *= dx;

        currentComputation.setComputationResult(sum);
        computationHistory.add(currentComputation);
        curCompId = getComputationHistorySize();
        String eventDesc = "Added Computation #" + curCompId
                + " of name: " + currentFunction.getFunction()
                + " and type: left sum to Riemman Sum Computation history.";
        EventLog.getInstance().logEvent(new Event(eventDesc));
        return sum;
    }

    // MODIFIES: this
    // EFFECTS: computes mid Riemman sum and adds it to computationHistory
    private double computeMidSum() {
        double a = currentComputation.getIntervalA();
        double dx = currentComputation.getDeltaX();
        int n = currentComputation.getNumOfRectanglesN();
        double sum = 0.0;
        int curCompId;

        for (int i = 1; i <= n; ++i) {
            sum += this.computeFunctionAtX(a + ((double)(i - 0.5f) * dx));
        }

        sum *= dx;
        currentComputation.setComputationResult(sum);
        computationHistory.add(currentComputation);
        curCompId = getComputationHistorySize();
        String eventDesc = "Added Computation #" + curCompId
                + " of name: " + currentFunction.getFunction()
                + " and type: midpoint sum to Riemman Sum Computation history.";
        EventLog.getInstance().logEvent(new Event(eventDesc));
        return sum;
    }

    // MODIFIES: this
    // EFFECTS: removes newest computation from Riemman Sum Computation history
    public void removeNewestComputation() {
        int historySize = this.getComputationHistorySize();

        if (historySize > 0) {
            String fnName = this.getComputationHistory().get(historySize - 1).getComputationFunction();
            String sumType = this.getComputationHistory().get(historySize - 1).getRiemmanSumTypeString();

            this.getComputationHistory().remove(historySize - 1);
            String eventDesc = "Removed Computation #" + historySize
                    + " of name: " + fnName + " and type: " + sumType
                    + " sum to Riemman Sum Computation history.";
            EventLog.getInstance().logEvent(new Event(eventDesc));
        }
    }

    // EFFECTS: returns RiemmanSum object as an JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("computation id", compId);
        json.put("current computation", currentComputation.toJson());
        json.put("computation function", currentFunction.toJson());
        json.put("computation history", computationListToJson());

        return json;
    }

    // EFFECTS: returns computation history as an JSON array object
    private JSONArray computationListToJson() {
        JSONArray computationsJsonArray = new JSONArray();

        for (Computation c : computationHistory) {
            computationsJsonArray.put(c.toJson());
        }

        return computationsJsonArray;
    }

    // MODIFIES: this
    // EFFECTS: sets this.compId to id
    public void setCompId(int id) {
        this.compId = id;
    }

    // getters
    public String getFunction() {
        return currentComputation.getComputationFunction();
    }

    public String getFunctionType() {
        return currentComputation.getComputationFunctionType();
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

    public String getRiemmanSumType() {
        return currentComputation.getRiemmanSumTypeString();
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

    public int getCompId() {
        return compId;
    }

}