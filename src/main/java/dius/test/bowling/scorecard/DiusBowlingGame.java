package dius.test.bowling.scorecard;

import dius.test.bowling.scorecard.exceptions.BowlingException;
import dius.test.bowling.scorecard.exceptions.FrameException;
import dius.test.bowling.scorecard.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class DiusBowlingGame implements BowlingGame {
    private final List<Frame> gameFrames;
    private Frame currentFrame;
    private static final int MAX_FRAMES_PER_GAME = 10;

    private int score;

    public DiusBowlingGame() {
        this.gameFrames = new ArrayList<>();
        this.score = 0;
    }

    private void setCurrentFrame(Frame newFrame) throws BowlingException, FrameException {
        if (this.gameFrames.size() == MAX_FRAMES_PER_GAME) {
            throw new BowlingException("Max frames per games exceeded!");
        }

        if (newFrame != null) {
            this.currentFrame = newFrame;
            this.gameFrames.add(this.currentFrame);
        } else {
            throw new FrameException("Frame is not initialized!");
        }
    }

    @Override
    public void roll(int noOfPins) throws BowlingException, FrameException {
        if (this.isNewFrame()) {
            this.setCurrentFrame(new Frame((this.gameFrames.size() + 1) == MAX_FRAMES_PER_GAME));
        }

        this.currentFrame.setRoll(noOfPins);
        this.calculateCurrentScore();
    }

    @Override
    public int score() {
        return this.score;
    }


    private void calculateCurrentScore() {
        if (this.gameFrames.size() == MAX_FRAMES_PER_GAME &&
                this.gameFrames.stream().allMatch(Frame::isStrike)) {
            this.score = 300;
        } else {
            this.updateFrameScores();
            this.score = this.gameFrames.stream()
                    .map(Frame::getScore)
                    .reduce(0, Integer::sum);
        }
    }

    private void updateFrameScores() {
        IntStream.range(0, this.gameFrames.size())
                .forEach(i -> {
                    Frame frame = this.gameFrames.get(i);
                    int frameScore = frame.getFramePins();
                    int bonusScore = 0;
                    if (i < this.gameFrames.size() - 1) {
                        if (frame.isSpare() &&
                                this.gameFrames.get(i + 1).getRoll1() != null
                        ) {
                            bonusScore = this.gameFrames.get(i + 1).getRoll1().getPins();
                        } else if (frame.isStrike()) {
                            bonusScore = this.gameFrames.get(i + 1).getFramePins();
                        }
                    }
                    frame.setFrameScore(frameScore + bonusScore);
                });
    }

    private boolean isNewFrame() {
        if (this.currentFrame == null) {
            return true;
        }

        if (!this.currentFrame.isLastFame() && this.currentFrame.isStrike()) {
            return true;
        }

        if (this.currentFrame.getRoll1() != null &&
                this.currentFrame.getRoll2() != null) {
            if (this.currentFrame.isLastFame()) {
                if (this.currentFrame.isSpare() || this.currentFrame.isStrike()) {
                    if (this.currentFrame.getBonusRoll() != null) {
                        return true;
                    }
                } else {
                    return true;
                }
            } else {
                return true;
            }
        }

        return false;
    }

}
