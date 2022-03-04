package persistence;

import model.Computation;
import model.RiemmanSum;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/*
 Super class providing helper methods for checking equality between RiemmanSum
 and Computation objects
 */
public class JsonTest {

    // EFFECTS: checks for equality between two Riemman Sums
    protected void sumEqualityCheckerHelper(RiemmanSum expected, RiemmanSum actual) {
        assertEquals(expected.getNumOfRectangles(), actual.getNumOfRectangles());
        assertEquals(expected.getFunction(), actual.getFunction());
        assertEquals(expected.getIntervalB(), actual.getIntervalB());
        assertEquals(expected.getIntervalA(), actual.getIntervalA());
        assertEquals(expected.getDeltaX(), actual.getDeltaX());
        assertEquals(expected.getCompId(), actual.getCompId());
        assertEquals(expected.getRiemmanSumType(), actual.getRiemmanSumType());
        assertEquals(expected.getFunctionType(), actual.getFunctionType());
        assertEquals(expected.getComputationHistorySize(), actual.getComputationHistorySize());
        checkComputationHistoriesHelper(expected.getComputationHistory(), actual.getComputationHistory());
    }

    // EFFECTS: checks for equality between two Computation history lists
    protected void checkComputationHistoriesHelper(List<Computation> expected, List<Computation> actual) {
        if (expected.size() != actual.size()) {
            fail("Expected computation history size differs from actual computation history size");
        }

        for (int i = 0; i < expected.size(); ++i) {
            checkComputationEqualityHelper(expected.get(i), actual.get(i));
        }
    }

    // EFFECTS: checks for equality between two Computation results
    protected void checkComputationEqualityHelper(Computation expected, Computation actual) {
        assertEquals(expected.getIntervalA(), actual.getIntervalA());
        assertEquals(expected.getIntervalB(), actual.getIntervalB());
        assertEquals(expected.getNumOfRectanglesN(), actual.getNumOfRectanglesN());
        assertEquals(expected.getDeltaX(), actual.getDeltaX());
        assertEquals(expected.getComputationNumber(), actual.getComputationNumber());
        assertEquals(expected.getRiemmanSumType(), actual.getRiemmanSumType());
        assertEquals(expected.getRiemmanSumTypeString(), actual.getRiemmanSumTypeString());
        assertEquals(expected.getComputationFunctionType(), actual.getComputationFunctionType());
        assertEquals(expected.getComputationFunction(), actual.getComputationFunction());
        assertEquals(expected.getComputationResult(), actual.getComputationResult());
    }

}
