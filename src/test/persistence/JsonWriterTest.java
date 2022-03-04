package persistence;

import model.*;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
    JUnit testing class for all writing functionality
    housed in the JsonWriter class
 */
class JsonWriterTest extends JsonTest {
    private RiemmanSum testSumWithHistory;
    private RiemmanSum testSumNoHistory;

    @BeforeEach
    void setup() {
        testSumWithHistory = new RiemmanSum("left", "linear", "5x", 1.1, 5.2, 10);
        testSumWithHistory.computeRiemmanSum();
        testSumWithHistory.recomputeAdjustedSum(5, "right");

        testSumNoHistory = new RiemmanSum("midpoint", "trigonometric", "-2sin(x)", 0.0, 3.5, 3);
    }

    @Test
    void testWriterSumNoComputationHistory() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterNoComputationHistory.json");
            writer.open();
            writer.write(testSumNoHistory);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNoComputationHistory.json");
            RiemmanSum newlyReadRiemmanSum = reader.read();

            sumEqualityCheckerHelper(testSumNoHistory, newlyReadRiemmanSum);
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        } catch (JSONException e) {
            fail("JSONException should not have been thrown");
        }
    }

    @Test
    void testWriterSumWithComputationHistory() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterWithComputationHistory.json");
            writer.open();
            writer.write(testSumWithHistory);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterWithComputationHistory.json");
            RiemmanSum newlyReadRiemmanSum = reader.read();

            sumEqualityCheckerHelper(testSumWithHistory, newlyReadRiemmanSum);
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        } catch (JSONException e) {
            fail("JSONException should not have been thrown");
        }
    }

    @Test
    void testWritingToInvalidFileName() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }
}
