package model;

import org.json.JSONObject;
import persistence.Writable;

import java.util.Stack;

/*
    Function parser that takes in a mathematical function and type, and provides
    utility for computing such function at a given x value.
 */
public class MathFunction implements Writable {
    enum FuncType {
        TRIGONOMETRIC,
        LOGARITHMIC,
        LINEAR
    }

    private final FuncType functionType;
    private final String unparsedFunction;
    private final String unparsedFunctionNoTrim;

    private double verticalCoeff;
    private String functionName;
    private int iterator;


    // REQUIRES: type has to be either Trigonometric, Logarithmic, or Linear
    //           unparsedFunction must abide by constraints outlined in README.md
    // EFFECTS: Constructs a math function with the function type (trig, log, linear) and its unparsed
    //          function input
    public MathFunction(String type, String inputFunction) {
        this.functionType = parseFnType(type.trim());
        this.unparsedFunctionNoTrim = inputFunction;
        this.unparsedFunction = inputFunction.trim().replace("*", "");

        this.verticalCoeff = 1.0; // Default values
        this.functionName = this.unparsedFunction;

        this.iterator = 0;
    }

    // EFFECTS: Constructs math function object from JSON file generated data.
    public MathFunction(String parsedFn, String fnType, String unparsedFn, int vertCoeff, int internalIterator) {
        this.functionName = parsedFn;
        this.functionType = parseFnType(fnType);
        this.unparsedFunction = unparsedFn;
        this.unparsedFunctionNoTrim = unparsedFn;
        this.verticalCoeff = vertCoeff;
        this.iterator = internalIterator;
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

    // DISCLAIMER: Can only compute with integer values of x. As such, massive error may accumulate. Parser is WIP
    // MODIFIES: this
    // EFFECTS: Computes linear function at the x value specified.
    private double computeLinearFunc(double x) {
        // Old method:
        // parseVertDisp();
        // return verticalCoeff * x;
        // New method:
        int newX = (int)x;
        String expression = unparsedFunctionNoTrim.replace("x", String.valueOf(newX));
        System.out.println(expression);
        Stack<Integer> bothOperands = new Stack<>();    // Operands to compute (ie: 7 and 2)
        Stack<Integer> operandResolver = new Stack<>(); // Resolves current operand (ie; "234" to int)
        int currentOp = 0;                              // Current operator being resolved
        int result = 0;

        for (int i = 0; i < expression.length(); ++i) {
            boolean isOperator = checkIfSupportedOp(expression.charAt(i));
            if (isOperator) {
                if (bothOperands.empty()) {
                    throw new ArithmeticException("Function is not correctly in post-fix");
                }
                int operand2 = bothOperands.peek();
                bothOperands.pop();

                if (bothOperands.empty()) {
                    throw new ArithmeticException("Function is not correctly in post-fix");
                }

                int operand1 = bothOperands.peek();
                bothOperands.pop();

                result = computeResult(operand1, operand2, expression.charAt(i));
                bothOperands.push(result);

            } else if (expression.charAt(i) == ' ') {
                if (!operandResolver.empty()) { // need this if space after an operator
                    for (int j = 0; !operandResolver.empty(); ++j) {
                        currentOp += operandResolver.peek() * Math.pow(10, j);
                        operandResolver.pop();
                    }
                    bothOperands.push(currentOp);
                    currentOp = 0;
                }
            } else if (expression.charAt(i) >= '0' && expression.charAt(i) <= '9') {
                operandResolver.push(expression.charAt(i) - '0'); // Convert op to an integer
            } else {
                throw new ArithmeticException("NaN");
            }
        }
        return result;
    }

    // EFFECTS: Returns true if character is one of (+, -, /, *); false otherwise
    private boolean checkIfSupportedOp(char ch) {
        switch (ch) {
            case '+':
                return true;
            case '-':
                return true;
            case '/':
                return true;
            case '*':
                return true;
            default:
                return false;
        }
    }

    // EFFECTS: Applies "op" operator onto operands op1 and op2.
    private int computeResult(int op1, int op2, char op) {
        switch (op) {
            case '+':
                return op1 + op2;
            case '-':
                return op1 - op2;
            case '*':
                return op1 * op2;
            case '/':
                return op1 / op2;
            default:
                return 0;
        }
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

    // EFFECTS: returns MathFunction object as an JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("function type", functionType);
        json.put("unparsed function string", unparsedFunction);
        json.put("vertical coefficient", verticalCoeff);
        json.put("parsed function string", functionName);
        json.put("internal function iterator", iterator);

        return json;
    }

    // getters
    public FuncType getFunctionType() {
        return functionType;
    }

    public String getFunction() {
        return unparsedFunction;
    }

}
