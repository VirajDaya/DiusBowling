package dius.test.bowling.simulation;

import dius.test.bowling.scorecard.BowlingGame;
import dius.test.bowling.scorecard.BowlingGameFactory;

import java.util.Arrays;

class DiusBowlingSimulator {
    public static void main(String... args) {
        int sampleRolls[][] = {
                {4, 4},
                {4, 6, 5, 0},
                {10, 5, 4},
                {4, 6, 5, 0, 10, 5, 4, 4, 10, 0, 5, 5, 0, 10, 5, 4, 10, 0, 5, 5, 9},
                {4, 6, 5, 0, 10, 5, 4, 4, 6, 0, 5, 5, 0, 10, 5, 4, 10, 0, 10},
                {10, 10, 10, 10, 10, 10, 10, 10, 10, 10}
        };

        try {
            Arrays.stream(sampleRolls)
                    .peek(r -> System.out.println("\nStaring new sample game"))
                    .forEach(sampleRoll -> {
                        BowlingGame game = BowlingGameFactory.makeBowlingGame();
                        Arrays.stream(sampleRoll)
                                .peek(i -> System.out.println("rolling " + i))
                                .forEach(i -> {
                                    try {
                                        game.roll(i);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                });
                        System.out.println("Total Score : " + game.score());
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
