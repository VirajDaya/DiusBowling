package dius.test.bowling.scorecard.model;

import dius.test.bowling.scorecard.exceptions.FrameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FrameTest {

    private Frame notLastFrame;
    private Frame lastFrame;

    @BeforeEach
    void setUp() {
        this.notLastFrame = new Frame(false);
        this.lastFrame = new Frame(true);
    }

    @Test
    @DisplayName("Two Rolls less than 10 should not make the Frame a Spare")
    void rollLessThan10ShouldNotMarkAsSpare() throws Exception {
        this.notLastFrame.setRoll(4);
        this.notLastFrame.setRoll(5);
        assertFalse(this.notLastFrame.isSpare());
        assertFalse(this.notLastFrame.isStrike());
    }

    @Test
    @DisplayName("Two Rolls equal to 10 pins should make the Frame a Spare")
    void twoRollsTotal10ShouldMarkAsSpare() throws Exception {
        this.notLastFrame.setRoll(5);
        this.notLastFrame.setRoll(5);
        assertTrue(this.notLastFrame.isSpare());
        assertFalse(this.notLastFrame.isStrike());
    }

    @Test
    @DisplayName("Rolling 10 on first roll should mark the Frame a Strike")
    void roll10OnFirstRollShouldMarkAsStrike() throws Exception {
        this.notLastFrame.setRoll(10);
        assertTrue(this.notLastFrame.isStrike());
        assertFalse(this.notLastFrame.isSpare());
    }

    @Test
    @DisplayName("Rolling more than two rolls in non last frame should not be allowed")
    void nonLastFrameShouldNotAllowThreeRolls() {
        assertThrows(FrameException.class, () -> {
            this.notLastFrame.setRoll(1);
            this.notLastFrame.setRoll(2);
            this.notLastFrame.setRoll(3);
        });
    }

    @Test
    @DisplayName("Spare in last frame should allow a bonus roll")
    void spareInLastFrameShouldAllowBonusRoll() {
        assertDoesNotThrow(() -> {
            this.lastFrame.setRoll(4);
            this.lastFrame.setRoll(6);
            this.lastFrame.setRoll(10);
        });
    }

    @Test
    @DisplayName("Spare in last frame should NOT allow more than one bonus roll")
    void spareInLastFrameShouldNotAllowTwoBonusRolls() {
        assertThrows(FrameException.class, () -> {
            this.lastFrame.setRoll(4);
            this.lastFrame.setRoll(6);
            this.lastFrame.setRoll(10);
            this.lastFrame.setRoll(10);
        });
    }


    @Test
    @DisplayName("Strike in last frame should allow two rolls")
    void strikeInLastFrameShouldAllowTwoBonusRolls() {
        assertDoesNotThrow(() -> {
            this.lastFrame.setRoll(10);
            this.lastFrame.setRoll(6);
            this.lastFrame.setRoll(10);
        });
    }

    @Test
    @DisplayName("Strike in last frame should NOT allow more than two bonus rolls")
    void strikeInLastFrameShouldNotAllowThreeBonusRolls() {
        assertThrows(FrameException.class, () -> {
            this.lastFrame.setRoll(10);
            this.lastFrame.setRoll(6);
            this.lastFrame.setRoll(10);
            this.lastFrame.setRoll(10);
        });
    }
}