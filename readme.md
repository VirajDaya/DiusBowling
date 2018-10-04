# Dius Bowling Score Calculator app
This is a simple application to calculate 10 pin bowling game score.

# How to build

Run `mvn clean package` to clean and build the application

# How to run the Sample game simulator

1. Go to the `target` directory
2. Run `java -jar DiUSBowlingScoreCard-1.0.0.jar` to run the sample bowling simulator

*NOTE : If the version has changed in the pom file, then use the corresponding version in jar file name*

# Explanation on the implemented solution

- The `BowlingGame` is a public interface to set `roll` and get `score` as per the problem specification
- Actual implementation is in the `DiusBowlingGame`, which can be instantiated using the `BowlingGameFactory` class
- Two model classes, `Frame` and `Roll`, are used to store information about each frame and roll
- Few exception classes are defined to capture specific errors
- Handles the bonus roll cases in the last frame for spare or strike
- Handles the maximum score for all strikes (300)



