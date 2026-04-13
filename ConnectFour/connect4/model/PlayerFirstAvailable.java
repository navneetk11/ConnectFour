package ca.yorku.eecs3311.connect4.model;

/**
 * PlayerFirstAvailable is a simple strategy that selects
 * the first available column from left to right.
 *
 * Strategy Pattern:
 * - Implements the Player interface
 * - Provides a basic move-selection behaviour
 *
 * Behaviour:
 * - Scans columns from left (0) to right
 * - Picks the first column that is not full
 *
 * This strategy is useful for
 * Demonstrating extensibility of the Strategy pattern
 */

public class PlayerFirstAvailable implements Player {

    @Override
    public Move getMove(ConnectFour game) {

        for (int col = 0; col < ConnectFourBoard.COLS; col++) {
            if (game.getBoard().landingRow(col) >= 0) {              // landingRow >= 0 means column is not full
                return new Move(col);                               // return first available column
            } 
        }

        return null;                                               // no valid move (board full)
    }

    @Override
    public String getName() {
        return "FirstAvailable";
    }
}
