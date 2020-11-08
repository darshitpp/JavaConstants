import operations.CircleArea;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathConstantsWithInterfaceImplementationAndConstantShadowingTest {

    @Test
    void calculate() {
        CircleArea area = new MathConstantsWithInterfaceImplementationAndConstantShadowing();

        double circleArea = area.calculate(1.0);
        // Fails
        assertEquals(3.14, circleArea);
    }
}