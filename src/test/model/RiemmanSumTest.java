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
        invalidSum = new RiemmanSum(1, Math.PI, 3); // [1, pi] with 3 rectangles
    }

    @Test
    void testConstructor(){
        // testSum construction
        assertEquals(0, testSum.getIntervalA());
        assertEquals(1, testSum.getIntervalB());
        assertEquals(5, testSum.getNumOfRectangles());
        assertEquals(0, testSum.getComputationHistorySize());

        // invalidSum construction
        assertEquals(1, invalidSum.getIntervalA());
        assertEquals(Math.PI, invalidSum.getIntervalB(), 0.1);
        assertEquals(3, invalidSum.getNumOfRectangles());
        assertEquals(0, invalidSum.getComputationHistorySize());

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


}