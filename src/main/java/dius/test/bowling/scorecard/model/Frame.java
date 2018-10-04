package dius.test.bowling.scorecard.model;


import dius.test.bowling.scorecard.exceptions.FrameException;

public class Frame {
    private static final int MAX_PINS_PER_FRAME = 10;

    private Roll roll1;
    private Roll roll2;
    private Roll bonusRoll;
    private boolean spare;
    private boolean strike;
    private final boolean lastFame;
    private int framePins;
    private int score;

    public Frame(Boolean lastFame) {
        this.spare = false;
        this.strike = false;
        this.lastFame = lastFame;
        this.framePins = 0;
        this.score = 0;
    }

    public void setRoll(int pins) throws FrameException {
        if (this.roll1 == null) {
            this.roll1 = new Roll(pins);
            this.framePins += pins;
            if (pins == MAX_PINS_PER_FRAME) {
                this.strike = true;
            }
        } else if (this.roll2 == null) {
            this.roll2 = new Roll(pins);
            this.framePins += pins;
            if (!this.strike && this.framePins == MAX_PINS_PER_FRAME) {
                this.spare = true;
            }
        } else if (this.lastFame && this.bonusRoll == null) {
            this.bonusRoll = new Roll(pins);
            this.framePins += pins;
        } else {
            throw new FrameException("This frame is already full! Create a new frame.");
        }
    }

    public Roll getRoll1() {
        return roll1;
    }

    public Roll getRoll2() {
        return roll2;
    }

    public Roll getBonusRoll() {
        return bonusRoll;
    }

    public int getFramePins() {
        return this.framePins;
    }

    public boolean isSpare() {
        return this.spare;
    }

    public boolean isStrike() {
        return this.strike;
    }

    public boolean isLastFame() {
        return this.lastFame;
    }

    public void setFrameScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }
}
