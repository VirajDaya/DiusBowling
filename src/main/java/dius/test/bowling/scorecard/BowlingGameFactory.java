package dius.test.bowling.scorecard;

public class BowlingGameFactory {
    public static BowlingGame makeBowlingGame() {
        return new DiusBowlingGame();
    }
}
