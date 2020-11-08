import constants.MathConstantsInterface;
import operations.CircleArea;

public class MathConstantsInterfaceWithoutImplementation implements CircleArea {
    public double calculate(double radius) {
        return MathConstantsInterface.PI * radius * radius;
    }
}
