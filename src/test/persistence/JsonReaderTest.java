package persistence;

import model.*;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
    JUnit testing class for all reading functionality
    housed in the JsonReader class
 */
class JsonReaderTest extends JsonTest {
    private RiemmanSum testSumWithHistory;
    private RiemmanSum testSumNoHistory;

    @BeforeEach
    void setup() {
        testSumWithHistory = new RiemmanSum("right", "logarithmic", "-2ln(x)", 1.5, 3.0, 4);
        testSumWithHistory.computeRiemmanSum();
        testSumWithHistory.addNewRiemmanSum("left", "linear", "11x", 0.1, 0.9, 9);

        testSumNoHistory = new RiemmanSum("midpoint", "trigonometric", "5tan(x)", 0.2, 0.9, 2);
    }

    @Test
    void testReaderSumWithNoComputationHistory() {
        JsonWriter writer = new JsonWriter("./data/testReaderNoComputationHistory.json");

        try {
            writer.open();
        } catch(IOException e) {
            fail("Unexpected IOException exception thrown.");
        }

        writer.write(testSumNoHistory);
        writer.close();

        JsonReader reader = new JsonReader("./data/testReaderNoComputationHistory.json");
        try {
            RiemmanSum newlyReadRiemmanSum = reader.read();
            sumEqualityCheckerHelper(testSumNoHistory, newlyReadRiemmanSum);
        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (JSONException e) {
            fail("File had invalid contents");
        }
    }


    @Test
    void testReaderSumWithComputationHistory() {
        JsonWriter writer = new JsonWriter("./data/testReaderWithComputationHistory.json");

        try {
            writer.open();
        } catch(IOException e) {
            fail("Unexpected IOException exception thrown.");
        }

        writer.write(testSumWithHistory);
        writer.close();

        JsonReader reader = new JsonReader("./data/testReaderWithComputationHistory.json");
        try {
            RiemmanSum newlyReadRiemmanSum = reader.read();
            sumEqualityCheckerHelper(testSumWithHistory, newlyReadRiemmanSum);
        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (JSONException e) {
            fail("File had invalid contents");
        }
    }


    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/nonExistentFile.json");

        try {
            testSumNoHistory = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        } catch (JSONException e) {
            fail("Unexpected JSONException was thrown.");
        }
    }

    @Test
    void testReaderInvalidFile() {
        JsonReader reader = new JsonReader("./data/testInvalidFileContents.json");

        try {
            testSumNoHistory = reader.read();
            fail("JSONException expected");
        } catch (IOException e) {
            fail("Unexpected IOException was thrown.");
        } catch (JSONException e) {
            // pass
        }
    }
}