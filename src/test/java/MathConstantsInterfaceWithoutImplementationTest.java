import operations.CircleArea;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathConstantsInterfaceWithoutImplementationTest {

    @Test
    void calculate() {
        CircleArea area = new MathConstantsInterfaceWithoutImplementation();

        double circleArea = area.calculate(1.0);
        assertEquals(3.14, circleArea);
    }
}