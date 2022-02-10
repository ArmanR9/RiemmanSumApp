package model;

import model.Computation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ComputationTest {
    Computation testComputationLEFT;
    Computation testComputationRIGHT;
    Computation testComputationMID;
    Computation testComputationINVALID;

    @BeforeEach
    void runBefore(){
        testComputationLEFT = new Computation(0, "left", "trigonometric", "5sin(x)", 10, 3, 5);
        testComputationRIGHT = new Computation(5, "right", "trig", "cos(x)", 0 , 2, 2);
        testComputationMID = new Computation(2, "midpoint", "logarithmic" , "2ln(x)", 2, 5, 10);
        testComputationINVALID = new Computation(2, "mid", "logarithmic" , "2ln(x)", 2, 5, 10);
    }

    @Test
    void testConstructor(){
        compConstructionTestHelper(testComputationLEFT, 0, Computation.SumType.LEFT,
                "trigonometric", "5sin(x)", 10, 3, 5);
        compConstructionTestHelper(testComputationRIGHT, 5, Computation.SumType.RIGHT,
                "trigonometric", "cos(x)",  0, 2, 2);
        compConstructionTestHelper(testComputationMID, 2, Computation.SumType.MIDPOINT,
                "logarithmic", "2ln(x)", 2, 5, 10);

        compConstructionTestHelper(testComputationINVALID, 2, null,
                "logarithmic", "2ln(x)", 2, 5, 10);

    }

    @Test
    void testProduceStats(){
        produceStatsTestHelper(testComputationLEFT, Computation.SumType.LEFT);
        produceStatsTestHelper(testComputationMID, Computation.SumType.MIDPOINT);
        produceStatsTestHelper(testComputationRIGHT, Computation.SumType.RIGHT);
    }

    @Test
    void testProduceStatsException(){
            try {
                testComputationINVALID.produceStats();
                fail();
            } catch (NullPointerException e) {
                // do nothing
            } catch (Exception e) {
                fail();
            }
    }

    @Test
    void testAdjustingN(){
        assertEquals(5, testComputationLEFT.getNumOfRectanglesN());
        assertEquals(10, testComputationMID.getNumOfRectanglesN());
        assertEquals(2, testComputationRIGHT.getNumOfRectanglesN());

        testComputationLEFT.setNumOfRectanglesN(6);
        testComputationMID.setNumOfRectanglesN(100);
        testComputationRIGHT.setNumOfRectanglesN(2147000);

        assertEquals(6, testComputationLEFT.getNumOfRectanglesN());
        assertEquals(100, testComputationMID.getNumOfRectanglesN());
        assertEquals(2147000, testComputationRIGHT.getNumOfRectanglesN());
    }

    @Test
    void testSettingComputingTime(){
        assertEquals(0.0, testComputationMID.getTimeToCompute());
        testComputationMID.setTimeToCompute(95.2);
        assertEquals(95.2, testComputationMID.getTimeToCompute());
    }

    private void produceStatsTestHelper(Computation testComp, Computation.SumType sumType) {
        List<String> stats = testComp.produceStats();

        assertEquals("Computation number: " + testComp.getComputationNumber(), stats.get(0));
        assertEquals("Function used: " + testComp.getComputationFunction(), stats.get(1));

        if (sumType == Computation.SumType.LEFT) {
            assertEquals("Riemman Sum Type: Left Sum", stats.get(2));
        } else if (sumType == Computation.SumType.RIGHT) {
            assertEquals("Riemman Sum Type: Right Sum", stats.get(2));
        } else if (sumType == Computation.SumType.MIDPOINT) {
            assertEquals("Riemman Sum Type: Midpoint Sum", stats.get(2));
        } else {
            assertEquals("Riemman Sum Type: INVALID", stats.get(2));
        }

        assertEquals("Interval: [" + testComp.getIntervalA() + ", " + testComp.getIntervalB() + "]",
                stats.get(3));

        assertEquals("Number of rectangles: " + testComp.getNumOfRectanglesN(), stats.get(4));
        assertEquals("Partition size: " + testComp.getDeltaX(), stats.get(5));

    }

    private void compConstructionTestHelper(Computation testComp, int compNum, Computation.SumType sumType, String funcType,
                                        String function, double a, double b, double n) {
        assertEquals(a, testComp.getIntervalA());
        assertEquals(b, testComp.getIntervalB());
        assertEquals(n, testComp.getNumOfRectanglesN());
        assertEquals((b - a) / n, testComp.getDeltaX());

        assertEquals(compNum, testComp.getComputationNumber());
        assertEquals(sumType, testComp.getRiemmanSumType());

        if(sumType != null) {
            assertEquals(sumType.name().toLowerCase(), testComp.getRiemmanSumTypeString());
        }

        assertEquals(funcType, testComp.getComputationFunctionType());
        assertEquals(function, testComp.getComputationFunction());
    }
}
