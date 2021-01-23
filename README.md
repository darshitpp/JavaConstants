#  The Java Constants Interface Anti-Pattern 

> This post was first published at my blog: https://darshit.dev/posts/java-constants-interface/

How do you define and use constants in Java?

Most advice on Internet has the following opinions:

1. Declare `public static final` for constants in a class
2. Do not use Interfaces for constants

The most common way to define a constant is in a class and using `public static final`. One can then use the constant in another class using `ClassName.CONSTANT_NAME`. Constants are usually defined in upper cases as a rule, atleast in Java.

So if I were to define a constant for the value of Pi(π), it would be something like:

```java
public final class Constants {
    public static final double PI = 3.14;
}
```

This can then be used as `Constants.PI` whenever we want to reference the value of Pi.

Another way one can define constants is by the use of interfaces. 

```java
public interface Constants {
    double PI = 3.14;
} 
```

However, this is not recommended by most sources on the internet. Why? Because it is an anti-pattern.

### But is it really an Anti-pattern?

Let's examine the difference by using both the methods.

1. Creating a Constants class:
```java
package constants;

public final class MathConstantsClass {
    public static final double PI = 3.14;
}
```

2. Creating an interface:
```java
package constants;

public interface MathConstantsInterface {
    double PI = 3.14;
}
```

Let us define another interface which will help us test both the above methods.
```java
package operations;

public interface CircleArea {
    double calculate(double radius);
}
```

The above interface would help us define a contract to calculate the area of a circle. As we know, the area of a circle is dependent only on its radius, and thus is reflected in the above interface.

The following class provides the implementation of calculating the area of a circle.

```java
import constants.MathConstantsClass;
import operations.CircleArea;

public class MathConstantsClassImplementation implements CircleArea {
    public double calculate(double radius) {
        return MathConstantsClass.PI * radius * radius;
    }
}
```

To test the the above code, let us write a Test class using JUnit.

```java
import operations.CircleArea;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathConstantsClassImplementationTest {

    @Test
    void calculate() {
        CircleArea area = new MathConstantsClassImplementation();

        double circleArea = area.calculate(1.0);
        assertEquals(3.14, circleArea);
    }
}
```

If you run the above piece of test code, the test would pass.

For testing how we can use the constants with Interface, let's write another class called `MathConstantsInterfaceImplementation`.

```java
import constants.MathConstantsInterface;
import operations.CircleArea;

public class MathConstantsInterfaceImplementation implements MathConstantsInterface, CircleArea {
    public double calculate(double radius) {
        return PI * radius * radius;
    }
}
```

Similarly a test for the above class is as follows:

```java
import operations.CircleArea;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathConstantsInterfaceImplementationTest {

    @Test
    void calculate() {
        CircleArea area = new MathConstantsInterfaceImplementation();

        double circleArea = area.calculate(1.0);
        assertEquals(3.14, circleArea);
    }
}
```

The above Test would pass. However, the argument against the implementation is that it is not a good practice as there could be field shadowing, and that will override the original value of the constant within the class.

It can be better understood with the following example:

```java
import constants.MathConstantsInterface;
import operations.CircleArea;

public class MathConstantsWithInterfaceImplementationAndConstantShadowing implements MathConstantsInterface, CircleArea {
    private static final double PI = 200;

    public double calculate(double radius) {
        return PI * radius * radius;
    }
}
```

If, by chance, someone overrode the value of `PI` inside the class, it would lead to an incorrect output. It can be easily verified by the following test.

```java
import operations.CircleArea;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathConstantsWithInterfaceImplementationAndConstantShadowingTest {

    @Test
    void calculate() {
        CircleArea area = new MathConstantsWithInterfaceImplementationAndConstantShadowing();

        double circleArea = area.calculate(1.0);
        assertEquals(3.14, circleArea);
    }
}
```

The above test fails. The answer returned by `calculate()` is `200.0` instead of the expected `3.14`. Another argument is, using the interface would pollute the namespace and also lead to the value propagated across the subclasses.

The above arguments are valid, and hold true. 

However, what no one mentions is that you can still directly use the constants from the interface *without* implementing the interface. Just like the first example where we use `MathConstantsClass.PI`, we can also use `MathConstantsInterface.PI` without affecting the namespace and inheritance and shadowing issues.

This can also be easily verified:

```java
import constants.MathConstantsInterface;
import operations.CircleArea;

public class MathConstantsInterfaceWithoutImplementation implements CircleArea {
    public double calculate(double radius) {
        return MathConstantsInterface.PI * radius * radius;
    }
}
```

Test class:

```java
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
```

It would make no difference to our way of implementation. Even the number of imports remain same. Moreover, you do not need additional boilerplate of `public static final` as members in an interface are `public static final` by default. 

```java
public static final double PI = 3.14;
```
vs
```java
double PI = 3.14;
```

What would you prefer? Cleaner code, anyone?

I have seen most constants almost grouped together if they are used throughout the application. You could also suggest that interface should only be used for contracts, and in most cases they are. However, keeping an interface for solely storing constants doesn't seem to be wrong to me either! 

Unless, of course, some developer tries to implement a class which solely contains constants -- which would beget the question -- WHY?

References:

1. [Constants in Java: Patterns and Anti-Patterns](https://www.baeldung.com/java-constants-good-practices)
