package dius.test.bowling.scorecard;

import dius.test.bowling.scorecard.exceptions.BowlingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiusBowlingGameTest {

    private BowlingGame testGame;

    @BeforeEach
    void setUp() {
        this.testGame = BowlingGameFactory.makeBowlingGame();
    }

    @Test
    @DisplayName("Rolling without a spare should include only frame score")
    void roll1And0ShouldScore1() throws Exception {
        this.testGame.roll(1);
        this.testGame.roll(0);
        assertEquals(1, this.testGame.score());
    }

    @Test
    @DisplayName("Rolling without a spare should include only frame score")
    void roll4And4ShouldScore8() throws Exception {
        this.testGame.roll(4);
        this.testGame.roll(4);
        assertEquals(8, this.testGame.score());
    }

    @Test
    @DisplayName("Rolling a spare should include next bowl in score")
    void roll4And6And5And0ShouldScore20() throws Exception {
        this.testGame.roll(4);
        this.testGame.roll(6);
        this.testGame.roll(5);
        this.testGame.roll(0);
        assertEquals(20, this.testGame.score());
    }

    @Test
    @DisplayName("Rolling a Strike should include next two bowls in score")
    void roll10And5And4ShouldScore28() throws Exception {
        this.testGame.roll(10);
        this.testGame.roll(5);
        this.testGame.roll(4);
        assertEquals(28, this.testGame.score());
    }

    @Test
    @DisplayName("Rolling 10 strikes should return maximum game score")
    void roll10StrikesShouldScore300() throws Exception {
        this.runRoll(10, 10);
        assertEquals(300, this.testGame.score());
    }

    @Test
    @DisplayName("Rolling 10 Frames without strikes or spares should not throw error")
    void roll10FramesShouldBeAllowed() throws Exception {
        this.runRoll(20, 1);
        assertEquals(20, this.testGame.score());
    }

    @Test
    @DisplayName("Rolling more than 10 Frames should throw an error")
    void roll11FramesShouldNotBeAllowed() {
        Assertions.assertThrows(BowlingException.class, () -> this.runRoll(21, 1));
    }

    @Test
    @DisplayName("Rolling a spare in the last frame should allow a bonus bowl")
    void rollSpareInLastFrameShouldAllowBonusBowl() {
        Assertions.assertDoesNotThrow(() -> {
            this.runRoll(19, 1);
            this.testGame.roll(9);
            this.testGame.roll(4);
        });
    }

    @Test
    @DisplayName("Rolling a spare in the last frame should NOT allow more than one bonus bowl")
    void rollSpareInLastFrameShouldNotAllowTwoBonusBowls() {
        Assertions.assertThrows(BowlingException.class, () -> {
            this.runRoll(19, 1);
            this.testGame.roll(9);
            this.testGame.roll(4);
            this.testGame.roll(4);
        });
    }

    @Test
    @DisplayName("Rolling a strike in Last Frame should allow two bonus bowls")
    void rollStrikeInLastFrameShouldAllowTwoBonusBowls() {
        Assertions.assertDoesNotThrow(() -> {
            this.runRoll(18, 1);
            this.testGame.roll(10);
            this.testGame.roll(4);
            this.testGame.roll(6);
        });
    }

    @Test
    @DisplayName("Rolling a strike in Last Frame should not allow more than two bonus bowls")
    void rollStrikeInLastFrameShouldNotAllowThreeBonusBowls() {
        Assertions.assertThrows(BowlingException.class, () -> {
            this.runRoll(18, 1);
            this.testGame.roll(10);
            this.testGame.roll(4);
            this.testGame.roll(6);
            this.testGame.roll(10);
        });
    }

    private void runRoll(int noOfRolls, int pinsPerRoll) throws Exception {
        for (int i = 0; i < noOfRolls; i++) {
            this.testGame.roll(pinsPerRoll);
        }
    }

    private void runRoll(int... pins) throws Exception {
        for (int pin : pins) {
            this.testGame.roll(pin);
        }
    }
}