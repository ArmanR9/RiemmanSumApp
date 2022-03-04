package persistence;

import model.Computation;
import model.MathFunction;
import model.RiemmanSum;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.List;
import java.util.ArrayList;

/*
 Represents a reader that reads JSON representation of RiemmanSum from file

 NOTE:
 The JSONReader is largely modelled off of
 https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */
public class JsonReader {
    private String src;

    // EFFECTS: constructs JsonReader and sets the source file to src
    public JsonReader(String src) {
        this.src = src;
    }

    // EFFECTS: reads RiemmanSum from file and returns it;
    // throws IOException if an error occurs reading data from file
    public RiemmanSum read() throws IOException, JSONException { // maybe add BadJSONfile exception too
        String jsonData = readFile(src);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseRiemmanSum(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }


    // EFFECTS: parses Riemman Sum from JSON object and returns it
    private RiemmanSum parseRiemmanSum(JSONObject jsonObject) {
        Computation currentComputation = parseComputation(jsonObject, false);
        MathFunction currentMathFn = parseMathFn(jsonObject);
        int compId = jsonObject.getInt("computation id");
        List<Computation> curComputationHistory = parseComputationHistory(jsonObject);

        return new RiemmanSum(compId, currentComputation, currentMathFn, curComputationHistory);
    }

    // EFFECTS: parses Computation object from JSON Object and returns it
    private Computation parseComputation(JSONObject jsonObject, boolean isParsingHistory) {
        JSONObject computationObj;

        if (!isParsingHistory) {
            computationObj = jsonObject.getJSONObject("current computation");
        } else {
            computationObj = jsonObject;
        }

        int compId = computationObj.getInt("computation number");
        String sumType = computationObj.getString("riemman sum type");
        String funcType = computationObj.getString("computation function type");
        String function = computationObj.getString("computation function");
        double a = computationObj.getDouble("interval a");
        double b = computationObj.getDouble("interval b");
        int n = computationObj.getInt("number of rectangles");
        double computationResult = computationObj.getDouble("computation result");

        Computation parsedComputation = new Computation(compId, sumType, funcType, function, a, b, n);
        parsedComputation.setComputationResult(computationResult);

        return parsedComputation;
    }

    // EFFECTS: parses MathFunction object from JSON Object and returns it
    private MathFunction parseMathFn(JSONObject jsonObject) {
        JSONObject functionObj = jsonObject.getJSONObject("computation function");

        String function = functionObj.getString("parsed function string");
        String functionType = functionObj.getString("function type");
        String unparsedFunction = functionObj.getString("unparsed function string");
        int vertCoeff = functionObj.getInt("vertical coefficient");
        int internalFnIterator = functionObj.getInt("internal function iterator");

        return new MathFunction(function, functionType, unparsedFunction, vertCoeff, internalFnIterator);
    }

    // EFFECTS: parses Computation History array in given json object and returns it as a list
    private List<Computation> parseComputationHistory(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("computation history");
        List<Computation> computationHistoryList = new ArrayList<>();

        for (Object json : jsonArray) {
            JSONObject nextComputationObj = (JSONObject) json;
            computationHistoryList.add(parseComputation(nextComputationObj, true));
        }

        return computationHistoryList;
    }

}


