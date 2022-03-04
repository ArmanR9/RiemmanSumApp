package persistence;

import model.Computation;
import model.MathFunction;
import model.RiemmanSum;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads JSON representation of RiemmanSum from file
public class JsonReader {
    private String src;

    // EFFECTS: constructs JsonReader and sets the source file to src
    public JsonReader(String src) {
        this.src = src;
    }

    // EFFECTS: reads RiemmanSum from file and returns it;
    // throws IOException if an error occurs reading data from file
    public RiemmanSum read() throws IOException { // maybe add BadJSONfile exception too
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
        Computation currentComputation = parseComputation(jsonObject);
        MathFunction currentMathFn = parseMathFn(jsonObject);
        int compId = jsonObject.getInt("computation id");


        return new RiemmanSum(compId, currentComputation, currentMathFn, null);
    }

    private Computation parseComputation(JSONObject jsonObject) {
        JSONObject computationObj = jsonObject.getJSONObject("current computation");

        int compId = computationObj.getInt("computation number");
        String sumType = computationObj.getString("riemman sum type");
        String funcType = computationObj.getString("computation function type");
        String function = computationObj.getString("computation function");
        double a = computationObj.getDouble("interval a");
        double b = computationObj.getDouble("interval b");
        int n = computationObj.getInt("number of rectangles");

        return new Computation(compId, sumType, funcType, function, a, b, n);
    }

    private MathFunction parseMathFn(JSONObject jsonObject) {
        return null;
    }


    private void addComputations() {

    }


    private void addFunction() {

    }


    /*
    private Computation addComputation(JSONObject jsonObject){
        int compNumber = Integer.valueOf(jsonObject.getString(""))
        return new Computation()
    }
     */
}
