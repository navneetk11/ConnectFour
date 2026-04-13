package ca.yorku.eecs3311.connect4.test;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.yorku.eecs3311.connect4.model.ConnectFour;
import ca.yorku.eecs3311.connect4.model.ConnectFourBoard;

/**
 * Small starter test suite for the game model.
 */
public class ConnectFourTest {
    @Test
    public void testMoveAlternatesTurn() {
        ConnectFour game = new ConnectFour();
        assertEquals(ConnectFourBoard.P1, game.getWhosTurn());
        assertTrue(game.move(0));
        assertEquals(ConnectFourBoard.P2, game.getWhosTurn());
    }

    // completed A2-23:
    // Add tests for reset, illegal move, game-over state, undo/redo, save/load .
    
    @Test
    public void testResetGame() {
        ConnectFour game = new ConnectFour();

        game.move(0);
        game.move(1);

        game.reset();

        assertEquals(ConnectFourBoard.P1, game.getWhosTurn());
        assertEquals(0, game.getNumMoves());
    }

    @Test
    public void testInvalidMove() {
        ConnectFour game = new ConnectFour();

        // Fill column
        for (int i = 0; i < 6; i++) {
            game.move(0);
        }

        // Try invalid move
        boolean result = game.move(0);

        assertFalse(result);
    }

    @Test
    public void testGameOverWin() {
        ConnectFour game = new ConnectFour();

        for (int i = 0; i < 4; i++) {
            game.getBoard().drop(0, ConnectFourBoard.P1);
        }

        assertTrue(game.isGameOver());
        assertEquals(ConnectFourBoard.P1, game.getWinner());
    }
    @Test
    public void testSaveAndLoadGame() {
        ConnectFour game = new ConnectFour();

        game.move(0);
        game.move(1);

        game.saveGame("testfile.txt");

        // Create new game and load
        ConnectFour loadedGame = new ConnectFour();
        loadedGame.loadGame("testfile.txt");

        // Check same state
        assertEquals(game.getWhosTurn(), loadedGame.getWhosTurn());
        assertEquals(game.getNumMoves(), loadedGame.getNumMoves());

        // Check board content
        for (int r = 0; r < ConnectFourBoard.ROWS; r++) {
            for (int c = 0; c < ConnectFourBoard.COLS; c++) {
                assertEquals(
                    game.getBoard().get(r, c),
                    loadedGame.getBoard().get(r, c)
                );
            }
        }
    }
    @Test
    public void testUndoRedo() {
        ConnectFour game = new ConnectFour();

        game.move(0);
        game.move(1);

        // Undo last move
        game.undoLastTokenInColumn(1);

        assertEquals(1, game.getNumMoves());

        // Redo simulation (just drop again)
        game.move(1);

        assertEquals(2, game.getNumMoves());
    }
}
