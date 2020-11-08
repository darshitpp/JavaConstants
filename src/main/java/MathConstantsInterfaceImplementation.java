import constants.MathConstantsInterface;
import operations.CircleArea;

public class MathConstantsInterfaceImplementation implements MathConstantsInterface, CircleArea {
    public double calculate(double radius) {
        return PI * radius * radius;
    }
}
