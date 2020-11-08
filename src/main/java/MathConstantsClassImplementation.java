import constants.MathConstantsClass;
import operations.CircleArea;

public class MathConstantsClassImplementation implements CircleArea {
    public double calculate(double radius) {
        return MathConstantsClass.PI * radius * radius;
    }
}
