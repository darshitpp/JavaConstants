import constants.MathConstantsInterface;
import operations.CircleArea;

public class MathConstantsWithInterfaceImplementationAndConstantShadowing implements MathConstantsInterface, CircleArea {
    // Shadowed value
    private static final double PI = 200;

    public double calculate(double radius) {
        return PI * radius * radius;
    }
}
