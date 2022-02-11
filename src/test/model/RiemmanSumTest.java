package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(-1.0, testSumSin.getIntervalA());
        assertEquals(3.0, testSumSin.getIntervalB());
        assertEquals(10, testSumSin.getNumOfRectangles());
        assertEquals(0, testSumSin.getComputationHistorySize());
        assertEquals((3.0 - (-1.0)) / 10.0, testSumSin.getDeltaX(), 0.001);

        assertEquals(5, testSumLog.getIntervalA());
        assertEquals(12, testSumLog.getIntervalB());
        assertEquals(6, testSumLog.getNumOfRectangles());
        assertEquals(0, testSumLog.getComputationHistorySize());
        assertEquals((12 - 5) / 6.0, testSumLog.getDeltaX(), 0.001);

        assertEquals(1.0, testSumLin.getIntervalA());
        assertEquals(3.0, testSumLin.getIntervalB());
        assertEquals(12, testSumLin.getNumOfRectangles());
        assertEquals(0, testSumLin.getComputationHistorySize());
        assertEquals((3.0 - 1.0) / 12.0, testSumLin.getDeltaX(), 0.001);

        assertEquals(-1, invalidSum.getIntervalA());
        assertEquals(-5, invalidSum.getIntervalB(), 0.1);
        assertEquals(3, invalidSum.getNumOfRectangles());
        assertEquals(0, invalidSum.getComputationHistorySize());
        assertEquals((-5 - (-1)) / 3.0, invalidSum.getDeltaX(), 0.001);
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
        assertEquals(13.262, testSumLog.computeRiemmanSum(), 0.01);
        assertEquals(1, testSumLog.getComputationHistorySize());
    }

    @Test
    void testMidSum(){
        assertEquals(0, testSumLin.getComputationHistorySize());
        assertEquals(-396, testSumLin.computeRiemmanSum(), 0.01);
        assertEquals(1, testSumLin.getComputationHistorySize());
    }

    @Test
    void testLeftSum(){
        assertEquals(0, testSumSin.getComputationHistorySize());
        assertEquals(17.07, testSumSin.computeRiemmanSum(), 0.01);
        assertEquals(1, testSumSin.getComputationHistorySize());
    }

    @Test
    void testAddingNewSums(){

    }

    @Test
    void testComputingReadjustedSums(){
        assertEquals(0, testSumLog.getComputationHistorySize());

        assertEquals(12.83, testSumLog.recomputeAdjustedSum(200, "midpoint"), 0.01);
        assertEquals(1, testSumLog.getComputationHistorySize());

        assertEquals(12.56, testSumLog.recomputeAdjustedSum(10, "left"), 0.01);
        assertEquals(2, testSumLog.getComputationHistorySize());

        assertEquals(0, testSumLin.getComputationHistorySize());
        assertEquals(-198, testSumLin.recomputeAdjustedSum(1, "left"), 0.01);
        assertEquals(1, testSumLin.getComputationHistorySize());

    }

    @Test
    void testAddingComputations(){
        assertEquals(0, testSumSin.getComputationHistorySize());

        testSumSin.computeRiemmanSum();
        assertEquals(1, testSumSin.getComputationHistorySize());
        assertEquals(17.07, testSumSin.getComputationHistory().get(0).getComputationResult(), 0.1);

        testSumSin.recomputeAdjustedSum(90, "right");
        assertEquals(2, testSumSin.getComputationHistorySize());
        assertEquals(20.1744, testSumSin.getComputationHistory().get(1).getComputationResult(), 0.1);

    }


}