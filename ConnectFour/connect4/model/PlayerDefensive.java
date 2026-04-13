package ca.yorku.eecs3311.connect4.model;

/**
 * PlayerDefensive is a strategy that attempts to block
 * the opponent from winning on their next move.
 *
 * Strategy Pattern-
 * - Implements the Player interface
 * - Provides defensive behaviour by analysing opponent threats
 *
 * Behaviour:
 * - Simulates opponent moves in each column
 * - If a move would allow the opponent to win, it blocks that column
 * - If no immediate threat exists, it falls back to a random move
 *
 * This strategy demonstrates-
 * Extensibility of the Strategy pattern
 */

public class PlayerDefensive implements Player {

    private char token;

    public PlayerDefensive(char token) {
        this.token = token;
    }

    /**
     * it chooses a move that blocks the opponent if they are about to win.
     *
     * This method simulates each possible move for the opponent and checks
     * if it results in a win. If so, it returns that column to block it.
     * Otherwise, it selects a fallback move using a random strategy.
     *
     * @param game the current game state
     * @return a Move object representing the selected column
     */
    
    @Override
    public Move getMove(ConnectFour game) {

        char opponent = game.getBoard().otherPlayer(token);           // Identify opponent token 

                                                                     // Try to block opponent winning move
        for (int col = 0; col < ConnectFourBoard.COLS; col++) {

            int row = game.getBoard().landingRow(col);     
            if (row < 0) continue;                                   // Skip column if it is full

                                                                    // simulate opponent move
            game.getBoard().set(row, col, opponent);

            if (game.getBoard().hasWon(opponent)) {                   // Check if this simulated move results in opponent winning
                game.getBoard().set(row, col, ConnectFourBoard.EMPTY);
                return new Move(col);                                  // Block opponent by playing in this column
            }

                                                                      
            game.getBoard().set(row, col, ConnectFourBoard.EMPTY);     // undo simulation
        }

        
        return new PlayerRandom().getMove(game);                       //if no threat found then  fallback to random move
    }

    @Override
    public String getName() {
        return "Defensive";
    }
}