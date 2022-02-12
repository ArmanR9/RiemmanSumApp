package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
    JUnit testing class for all functionality
    housed in the RiemmanSum class
 */
class RiemmanSumTest {
    RiemmanSum testSumSin;
    RiemmanSum testSumLog;
    RiemmanSum testSumLin;
    RiemmanSum invalidSum;


    @BeforeEach
    void runBefore(){
        testSumSin = new RiemmanSum("left", "trigonometric",
                "13sin(x)", -1, 3, 10);

        testSumLog = new RiemmanSum("right", "logarithmic",
                "2log(x)", 5, 12, 6);

        testSumLin = new RiemmanSum("midpoint", "linear",
                "-99x", 1, 3, 12);

        invalidSum = new RiemmanSum("left", "logarithmic",
                "-2ln(x)", -1, -5, 3);

    }

    @Test
    void testConstructor(){
        // SinSum construction
        constructionTestHelper(testSumSin, -1, 3, 10, "13sin(x)");

        // LogSum construction
        constructionTestHelper(testSumLog, 5, 12, 6, "2log(x)");

        // LinearSum construction
        constructionTestHelper(testSumLin, 1.0, 3.0, 12, "-99x");

        // InvalidSum construction
        constructionTestHelper(invalidSum, -1, -5, 3, "-2ln(x)");
    }


    @Test
    void testGetFunction(){
        assertEquals("13sin(x)", testSumSin.getFunction());
        assertEquals("-99x", testSumLin.getFunction());
        assertEquals("2log(x)", testSumLog.getFunction());
    }

    @Test
    void testInvalidSums() {
        assertEquals(Double.NaN, invalidSum.computeRiemmanSum());
        assertEquals(Double.NaN, invalidSum.recomputeAdjustedSum(5, "right"));
        assertEquals(Double.NaN, invalidSum.recomputeAdjustedSum(5, "midpoint"));
    }

    @Test
    void testRightSum(){
        assertEquals(0, testSumLog.getComputationHistorySize());
        assertEquals(13.262, testSumLog.computeRiemmanSum(), 0.015);
        assertEquals(1, testSumLog.getComputationHistorySize());
    }

    @Test
    void testMidSum(){
        assertEquals(0, testSumLin.getComputationHistorySize());
        assertEquals(-396, testSumLin.computeRiemmanSum(), 0.015);
        assertEquals(1, testSumLin.getComputationHistorySize());
    }

    @Test
    void testLeftSum(){
        assertEquals(0, testSumSin.getComputationHistorySize());
        assertEquals(17.07, testSumSin.computeRiemmanSum(), 0.015);
        assertEquals(1, testSumSin.getComputationHistorySize());
    }

    @Test
    void testAddingNewSums(){
        assertEquals(5, testSumLog.getIntervalA());
        assertEquals(12, testSumLog.getIntervalB());
        assertEquals(6, testSumLog.getNumOfRectangles());
        assertEquals(0, testSumLog.getComputationHistorySize());
        assertEquals((12 - 5) / 6.0, testSumLog.getDeltaX(), 0.015);
        assertEquals("2log(x)", testSumLog.getFunction());

        testSumLog.addNewRiemmanSum("midpoint", "linear", "5x", 1, 2, 3);
        assertEquals(1, testSumLog.getIntervalA());
        assertEquals(2, testSumLog.getIntervalB());
        assertEquals(3, testSumLog.getNumOfRectangles());
        assertEquals(0, testSumLog.getComputationHistorySize());
        assertEquals((2 - 1) / 3.0, testSumLog.getDeltaX(), 0.015);
        assertEquals("5x", testSumLog.getFunction());

    }

    @Test
    void testComputingReadjustedSums(){
        assertEquals(0, testSumLog.getComputationHistorySize());

        assertEquals(12.83, testSumLog.recomputeAdjustedSum(200, "midpoint"), 0.015);
        assertEquals(1, testSumLog.getComputationHistorySize());

        assertEquals(12.56, testSumLog.recomputeAdjustedSum(10, "left"), 0.015);
        assertEquals(2, testSumLog.getComputationHistorySize());

        assertEquals(0, testSumLin.getComputationHistorySize());
        assertEquals(-198, testSumLin.recomputeAdjustedSum(1, "left"), 0.015);
        assertEquals(1, testSumLin.getComputationHistorySize());

        assertEquals(17.07, testSumSin.computeRiemmanSum(), 0.015);
        assertEquals(19.899, testSumSin.recomputeAdjustedSum(50, "midpoint"), 0.015);

    }

    @Test
    void testAddingComputations(){
        assertEquals(0, testSumSin.getComputationHistorySize());

        testSumSin.computeRiemmanSum();
        assertEquals(1, testSumSin.getComputationHistorySize());
        assertEquals(17.07, testSumSin.getComputationHistory().get(0).getComputationResult(), 0.015);

        testSumSin.recomputeAdjustedSum(90, "right");
        assertEquals(2, testSumSin.getComputationHistorySize());
        assertEquals(20.1744, testSumSin.getComputationHistory().get(1).getComputationResult(), 0.015);

    }

    private void constructionTestHelper(RiemmanSum testObj, double a, double b, int n, String func) {
        assertEquals(a, testObj.getIntervalA());
        assertEquals(b, testObj.getIntervalB());
        assertEquals(n, testObj.getNumOfRectangles());
        assertEquals(0, testObj.getComputationHistorySize());
        assertEquals(func, testObj.getFunction());
        assertEquals((b - a) / (double)n, testObj.getDeltaX(), 0.015);
    }

}