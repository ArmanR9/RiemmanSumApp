package model;

/*
    Function parser that takes in a mathematical function and type, and provides
    a utility for computing such function at a given x value.
 */
public class MathFunction {
    enum FuncType {
        TRIGONOMETRIC,
        LOGARITHMIC,
        LINEAR
    }

    private FuncType functionType;
    private String unparsedFunction;

    private double verticalCoeff;
    private String functionName;
    private int iterator;


    // REQUIRES: Type to be either trigonometric, logarithmic, or linear and function has to abide by form
    //           marked in README.md
    // EFFECTS: Constructs a math function with the function type (trig, log, linear) and its unparsed, human-readable
    //          function input
    public MathFunction(String type, String unparsedFunction) {
        this.functionType = parseFnType(type.trim());
        this.unparsedFunction = unparsedFunction.trim().replace("*", "");

        this.verticalCoeff = 1.0; // Default values
        this.functionName = null;

        this.iterator = 0;
    }

    // REQUIRES: type != null or empty
    // MODIFIES: this
    // EFFECTS: Converts unparsed function type String to appropriate class in FuncType enum
    private FuncType parseFnType(String type) {
        type = type.toLowerCase();
        FuncType parsedFuncType = null;

        if (type.contains("trig")) {
            parsedFuncType = FuncType.TRIGONOMETRIC;
        } else if (type.contains("log")) {
            parsedFuncType = FuncType.LOGARITHMIC;
        } else if (type.contains("linear")) {
            parsedFuncType = FuncType.LINEAR;
        }

        return parsedFuncType;
    }

    // MODIFIES: this
    // EFFECTS: Calls appropriate mathematical function and returns its computation result for a given x.
    public double applyComputation(double x) {

        if (functionType != null) {
            if (functionType.equals(FuncType.TRIGONOMETRIC)) {
                return computeTrigFunc(x);
            } else if (functionType.equals(FuncType.LOGARITHMIC)) {
                return computeLogFunc(x);
            } else {
                return computeLinearFunc(x);
            }
        } else {
            return Double.NaN;
        }
    }

    // MODIFIES: this
    // EFFECTS: Computes trigonometric function at the x value specified.
    private double computeTrigFunc(double x) {
        parseVertDisp();
        parseTrigFunc();

        switch (functionName) {
            case "sin":
                return verticalCoeff * Math.sin(x);
            case "cos":
                return verticalCoeff * Math.cos(x);
            case "tan":
                return verticalCoeff * Math.tan(x);
            default:
                return Double.NaN; // No support for csc, sec, cotx, etc. yet
        }
    }


    // MODIFIES: this
    // EFFECTS: Computes logarithmic function at the x value specified.
    private double computeLogFunc(double x) {
        parseVertDisp();
        parseLogFunc();

        if ("log".equals(functionName)) {
            return verticalCoeff * Math.log10(x);
        } else {
            return verticalCoeff * Math.log(x); // ln(x)
        }
    }

    // MODIFIES: this
    // EFFECTS: Computes linear function at the x value specified.
    private double computeLinearFunc(double x) {
        parseVertDisp();

        return verticalCoeff * x;
    }

    // MODIFIES: this
    // EFFECTS: Iterates through the input function string for its vertical display coefficient and sets it to
    //          this.verticalCoeff, in addition to updating the input function iterator position.
    private void parseVertDisp() {
        StringBuilder vertDisp = new StringBuilder();
        int it = 0;
        boolean isNegative = false;

        if (unparsedFunction.charAt(0) == '-') {
            isNegative = true;
            ++it; // move iterator one character forward to avoid negative sign
        }

        while (Character.isDigit(unparsedFunction.charAt(it))) {
            vertDisp.append(unparsedFunction.charAt(it));
            ++it;
        }

        double coeff = 1.0;

        if (vertDisp.length() != 0) {
            coeff = Double.parseDouble(vertDisp.toString().trim());
        }

        if (isNegative) {
            this.verticalCoeff = -1.0 * coeff;
        } else {
            this.verticalCoeff = coeff;
        }

        this.iterator = it;
    }

    // MODIFIES: this
    // EFFECTS: Iterates through the input function string for its trigonometric function and sets it to
    //          this.functionName, in addition to updating the input function iterator position.
    private void parseTrigFunc() {
        int i = this.iterator;

        StringBuilder trigOp = new StringBuilder();

        for (int j = 0; j < 3; ++j) {
            trigOp.append(unparsedFunction.charAt(i));
            ++i;
        }

        this.functionName = trigOp.toString().toLowerCase();

        this.iterator = i;
    }

    // MODIFIES: this
    // EFFECTS: Iterates through the input function string for its logarithmic function and sets it to
    //          this.functionName, in addition to updating the input function iterator position.
    private void parseLogFunc() {
        int i = this.iterator;

        StringBuilder logOp = new StringBuilder();

        for (int j = 0; j < 3; ++j) { // Needs a better check, maybe when we encounter parenthesis?
            logOp.append(unparsedFunction.charAt(i));
            ++i;
        }

        if (logOp.toString().contains("log")) {
            this.functionName = "log";
        } else { // default
            this.functionName = "ln";
            --i; // since ln is only two letters long
        }

        this.iterator = i;
    }

    // getters
    public FuncType getFunctionType() {
        return functionType;
    }

    public String getFunction() {
        return unparsedFunction;
    }

}
