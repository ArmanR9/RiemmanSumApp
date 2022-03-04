package persistence;

import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest {
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
    void testWriterNoComputationHistory() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterNoComputationHistory.json");
            writer.open();
            writer.write(testSumNoHistory);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNoComputationHistory.json");
            testSumNoHistory = reader.read();
            assertEquals("-2sin(x)", testSumNoHistory.getFunction());
            assertEquals("trigonometric", testSumNoHistory.getFunctionType());
            assertEquals(3.5, testSumNoHistory.getIntervalB(), 0.01);
            assertEquals(0.0, testSumNoHistory.getIntervalA());
            assertEquals(3, testSumNoHistory.getNumOfRectangles());
            assertEquals("midpoint", testSumNoHistory.getRiemmanSumType());
            assertEquals((3.5 - 0) / 3.0, testSumNoHistory.getDeltaX(), 0.015);
            assertEquals(0, testSumNoHistory.getComputationHistorySize());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterComputationHistory() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterWithComputationHistory.json");
            writer.open();
            writer.write(testSumWithHistory);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterWithComputationHistory.json");
            testSumWithHistory = reader.read();

            assertEquals("5x", testSumWithHistory.getFunction());
            assertEquals("linear", testSumWithHistory.getFunctionType());
            assertEquals(5.2, testSumWithHistory.getIntervalB(), 0.01);
            assertEquals(1.1, testSumWithHistory.getIntervalA());
            assertEquals(5, testSumWithHistory.getNumOfRectangles());
            assertEquals("right", testSumWithHistory.getRiemmanSumType());
            assertEquals((5.2 - 1.1) / 5.0, testSumWithHistory.getDeltaX(), 0.015);
            assertEquals(2, testSumWithHistory.getComputationHistorySize());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWritingToInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }
}
