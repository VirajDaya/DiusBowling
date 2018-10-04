package dius.test.bowling.scorecard;

public interface BowlingGame {

    void roll(int noOfPins) throws Exception;

    int score();
}
