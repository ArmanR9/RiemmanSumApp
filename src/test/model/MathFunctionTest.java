package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MathFunctionTest {
    MathFunction testFnTan;
    MathFunction testFnSin;
    MathFunction testFnCos;
    MathFunction testFnLinear;
    MathFunction testFnLog;
    MathFunction testFnLn;

    @BeforeEach
    void runBefore(){
        testFnTan = new MathFunction("Trigonometric", "9tan(x)");
        testFnSin = new MathFunction("trigonometric", "-5sin(x)");
        testFnCos = new MathFunction("trig", "-2cos(x)");
        testFnLinear = new MathFunction("linear", "-2x");
        testFnLog = new MathFunction("Logarithmic", "log(x)");
        testFnLn = new MathFunction("logarithmic", "5ln(x)");
    }

    @Test
    void testConstructor(){
        assertEquals(MathFunction.FuncType.TRIGONOMETRIC, testFnTan.getFunctionType());
        assertEquals("9tan(x)", testFnTan.getFunction());

        assertEquals(MathFunction.FuncType.LINEAR, testFnLinear.getFunctionType());
        assertEquals("-2x", testFnLinear.getFunction());

        assertEquals(MathFunction.FuncType.LOGARITHMIC, testFnLog.getFunctionType());
        assertEquals("log(x)", testFnLog.getFunction());

        assertEquals(MathFunction.FuncType.LOGARITHMIC, testFnLn.getFunctionType());
        assertEquals("5ln(x)", testFnLn.getFunction());

        assertEquals(MathFunction.FuncType.TRIGONOMETRIC, testFnSin.getFunctionType());
        assertEquals("-5sin(x)", testFnSin.getFunction());

        assertEquals(MathFunction.FuncType.TRIGONOMETRIC, testFnCos.getFunctionType());
        assertEquals("-2cos(x)", testFnCos.getFunction());

    }

    @Test
    void testApplyingComputation(){
        // Test accumulated behavior as applyComputation is mutator
        assertEquals(-30.42, testFnTan.applyComputation(5), 0.015);
        assertEquals(9.0, testFnTan.applyComputation(Math.PI / 4.0), 0.015);

        assertEquals(-0.2, testFnLinear.applyComputation(0.1), 0.015);
        assertEquals(199.0, testFnLinear.applyComputation(-99.5), 0.015);

        assertEquals(1.243, testFnLog.applyComputation(5 + 25.0/2.0), 0.015);
        assertEquals(0, testFnLog.applyComputation(1.0), 0.015);
        assertEquals(4.30, testFnLog.applyComputation(19999.5), 0.015);

        assertEquals(1.197, testFnLn.applyComputation(1.0 + 2.3/8.5), 0.015);
        assertEquals(172.69, testFnLn.applyComputation(1001010101010101.2), 0.015);

        assertEquals(0.0, testFnSin.applyComputation(Math.PI), 0.015);
        assertEquals(-1.207, testFnSin.applyComputation((Math.PI / 3.5) + 2), 0.015);

        assertEquals(-2.0*(1.0/Math.sqrt(2)), testFnCos.applyComputation(Math.PI/4), 0.015);
        assertEquals(-1.929, testFnCos.applyComputation(2.3 + (8.5 * 0.5)), 0.015);
    }

    @Test
    void testApplyingComputationNaN(){
        MathFunction testFnINVALID = new MathFunction("trigonometric", "10sen(x)");
        MathFunction testFnINVALID2 = new MathFunction("loogarithmic", "222loog(x)");

        assertTrue(Double.isNaN(testFnINVALID.applyComputation(5.0)));
        assertTrue(Double.isNaN(testFnINVALID2.applyComputation(2.2)));
    }
}
