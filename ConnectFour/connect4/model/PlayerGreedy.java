package ca.yorku.eecs3311.connect4.model;

import java.util.List;

/**
 * A simple greedy strategy.
 *
 * This is intentionally modest so students can improve or replace it.
 * 
 * 
 * 
 *  Improved with:
 *    - 2-move lookahead (avoids moves that let opponent win next turn)
 *    - Threat counting (prefers moves creating multiple winning chances)
 *
 */


public class PlayerGreedy extends PlayerRandom {
    private final char me;

    public PlayerGreedy(char me) {
        this.me = me;
    }

    @Override
    public Move getMove(ConnectFour game) {
        List<Integer> legal = this.legalColumns(game);
        if (legal.isEmpty()) return null;
        
        // Step 1: Win if possible
        
        for (int col : legal) {
            if (this.isWinningMove(game, col, this.me)) return new Move(col);
        }
        
        // Step 2: Block opponent
        
        char other = game.getBoard().otherPlayer(this.me);
        for (int col : legal) {
            if (this.isWinningMove(game, col, other)) return new Move(col);
        }
        
  
        
     // Step 3: Heuristic scoring
        
        int bestCol = legal.get(0);
        int bestScore = Integer.MIN_VALUE;
        for (int col : legal) {
            int score = this.scoreAfterMove(game, col, this.me);
           
            if (this.allowsOpponentWinNext(game, col)) {     //  Penalize moves that allow opponent to win next turn
                score -= 1000; 
            }

            
            score += 50 * this.countWinningMoves(game, col, this.me);  // Count number of future winning chances 

            
            if (score > bestScore || (score == bestScore && col < bestCol)) {
                bestScore = score;
                bestCol = col;
            }
        }
        return new Move(bestCol);
    }

    private boolean isWinningMove(ConnectFour game, int col, char player) {
        ConnectFour copy = game.copy();
        if (copy.getBoard().drop(col, player) < 0) return false;
        return copy.getBoard().hasWon(player);
    }

    protected int scoreAfterMove(ConnectFour game, int col, char player) {
        ConnectFour copy = game.copy();
        int row = copy.getBoard().drop(col, player);
        if (row < 0) return Integer.MIN_VALUE;

        int centerPreference = 10 - Math.abs(3 - col);
        int longest = this.longestLineThrough(copy.getBoard(), row, col, player);
        return 100 * longest + centerPreference;
    }

    private int longestLineThrough(ConnectFourBoard board, int row, int col, char player) {
        int best = 1;
        best = Math.max(best, this.lineLength(board, row, col, 0, 1, player));
        best = Math.max(best, this.lineLength(board, row, col, 1, 0, player));
        best = Math.max(best, this.lineLength(board, row, col, 1, 1, player));
        best = Math.max(best, this.lineLength(board, row, col, 1, -1, player));
        return best;
    }

    private int lineLength(ConnectFourBoard board, int row, int col, int drow, int dcol, char player) {
        return 1 + this.countDirection(board, row, col, drow, dcol, player)
                 + this.countDirection(board, row, col, -drow, -dcol, player);
    }

    private int countDirection(ConnectFourBoard board, int row, int col, int drow, int dcol, char player) {
        int count = 0;
        int rr = row + drow;
        int cc = col + dcol;
        while (board.validCoordinate(rr, cc) && board.get(rr, cc) == player) {
            count++;
            rr += drow;
            cc += dcol;
        }
        return count;
    }
    

    @Override
    public String getName() {
        return "Greedy";
    }
 // Completed A2-21:
 //   Improved greedy strategy by adding:
 // - 2-move lookahead to avoid moves that allow opponent to win
 // - threat counting to prioritize moves that create multiple winning opportunities


    /**
     * Checks if playing in a given column would allow the opponent
     * to win on their next move.
     *
     * This implements a simple 2-step lookahead:
     * - Simulate current player's move
     * - Check if opponent can win immediately after
     *
     * @param game the current game state
     * @param col the column being evaluated
     * @return true if the move is risky (opponent can win next), false otherwise
     */
       
    private boolean allowsOpponentWinNext(ConnectFour game, int col) {

        ConnectFour copy = game.copy();                               // Create a copy to simulate moves safely

        if (copy.getBoard().drop(col, this.me) < 0) return true;      // Simulate current move

        char opponent = copy.getBoard().otherPlayer(this.me);

        for (int c = 0; c < ConnectFourBoard.COLS; c++) {               // Check all opponent responses
            if (copy.getBoard().drop(c, opponent) >= 0) {               // Simulate opponent move
                if (copy.getBoard().hasWon(opponent)) {                  // If this leads to a win - count it as a threat
                    return true;
                }
                copy.undoLastTokenInColumn(c);                          //undo simulated opponent move
            }
        }

        return false;
    }
    
    /**
     * Counts how many winning moves are available after making a move.
     *
     * This helps identify moves that create multiple future threats,
     * increasing chances of winning in subsequent turns.
     *
     * @param game the current game state
     * @param col the column being evaluated
     * @param player the player making the move
     * @return number of winning moves available after this move
     */
    private int countWinningMoves(ConnectFour game, int col, char player) {

        ConnectFour copy = game.copy();                             // Create a copy to simulate moves safely

        if (copy.getBoard().drop(col, player) < 0) return 0;        // Simulate current move

        int count = 0;

     
        for (int c = 0; c < ConnectFourBoard.COLS; c++) {                 // Check all possible next moves

            ConnectFour test = copy.copy();                                // Create new copy for each simulation

            if (test.getBoard().drop(c, player) >= 0) {                    // Simulate next move

                if (test.getBoard().hasWon(player)) {                      // If this leads to a win - count it as a threat
                    count++;
                }
            }
        }

        return count;
    }
}
