package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RiemmanSumTest {
    RiemmanSum testSum;
    RiemmanSum invalidSum;

    @BeforeEach
    void runBefore(){
        // Function : 4 * sqrt(1 - x^2)
        testSum = new RiemmanSum(0, 1, 5); // [0, 1] with 5 rectangles
        invalidSum = new RiemmanSum(0.5, Math.PI, 3); // [0.5, pi] with 3 rectangles
    }


    @Test
    void testConstructor(){
        // testSum construction
        assertEquals(0, testSum.getIntervalA());
        assertEquals(1, testSum.getIntervalB());
        assertEquals(5, testSum.getNumOfRectangles());
        assertEquals(0, testSum.getComputationHistorySize());

        double a = testSum.getIntervalA();
        double b = testSum.getIntervalB();
        int n = testSum.getNumOfRectangles();
        assertEquals((b - a) / (double)n, testSum.getDeltaX());

        // invalidSum construction
        assertEquals(0.5, invalidSum.getIntervalA());
        assertEquals(Math.PI, invalidSum.getIntervalB(), 0.1);
        assertEquals(3, invalidSum.getNumOfRectangles());
        assertEquals(0, invalidSum.getComputationHistorySize());

        a = invalidSum.getIntervalA();
        b = invalidSum.getIntervalB();
        n = invalidSum.getNumOfRectangles();
        assertEquals((b - a) / (double)n, invalidSum.getDeltaX());

    }

    @Test
    void testSumComputationException() {
        tryCatchSetup(invalidSum, Computation.SumType.LEFT);
        tryCatchSetup(invalidSum, Computation.SumType.RIGHT);
        tryCatchSetup(invalidSum, Computation.SumType.MIDPOINT);
    }


    @Test
    void testRightSum(){
        assertEquals(0, testSum.getComputationHistorySize());
        assertEquals(2.63704, testSum.computeRightSum(), 0.1);
        assertEquals(1, testSum.getComputationHistorySize());
    }

    @Test
    void testMidSum(){
        assertEquals(0, testSum.getComputationHistorySize());
        assertEquals(3.171987, testSum.computeMidSum(), 0.1);
        assertEquals(1, testSum.getComputationHistorySize());
    }

    @Test
    void testLeftSum(){
        assertEquals(0, testSum.getComputationHistorySize());
        assertEquals(3.43704, testSum.computeLeftSum(), 0.1);
        assertEquals(1, testSum.getComputationHistorySize());
    }

    @Test
    void testAddingComputations(){
        assertEquals(0, testSum.getComputationHistorySize());

        testSum.computeLeftSum();
        assertEquals(1, testSum.getComputationHistorySize());
        assertEquals(3.43704, testSum.getComputationHistory().get(0), 0.1);

        testSum.computeRightSum();
        assertEquals(2, testSum.getComputationHistorySize());
        assertEquals(2.63704, testSum.getComputationHistory().get(1), 0.1);


    }

    private void tryCatchSetup(RiemmanSum sumObj, Computation.SumType type) {
        if (type == Computation.SumType.LEFT) {
            try {
                sumObj.computeLeftSum();
                fail();
            } catch (ArithmeticException e) {
                // do nothing
            } catch (Exception e) {
                fail();
            }
        } else if (type == Computation.SumType.RIGHT) {
            try {
                sumObj.computeRightSum();
                fail();
            } catch (ArithmeticException e) {
                // do nothing
            } catch (Exception e) {
                fail();
            }
        } else if (type == Computation.SumType.MIDPOINT) {
            try {
                sumObj.computeMidSum();
                fail();
            } catch (ArithmeticException e) {
                // do nothing
            } catch (Exception e) {
                fail();
            }
        }
    }


}