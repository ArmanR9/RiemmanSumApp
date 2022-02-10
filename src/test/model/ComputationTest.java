package model;

import model.Computation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ComputationTest {
    Computation testComputation;

    @BeforeEach
    void runBefore(){
        testComputation = new Computation(0, "left", "sin(x)", 10, 3, 5);
    }
}
