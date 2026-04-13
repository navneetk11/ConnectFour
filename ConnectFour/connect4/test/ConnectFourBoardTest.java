package ca.yorku.eecs3311.connect4.test;

import static org.junit.Assert.*;

import org.junit.Test;

import ca.yorku.eecs3311.connect4.model.ConnectFourBoard;

/**
 * Small starter test suite.
 *
 * completed A2-22:
 * Add many more tests as you develop the assignment, especially for:
 * - horizontal / diagonal wins
 * - invalid moves
 * - full-column moves etc 
 */
public class ConnectFourBoardTest {
    @Test
    public void testDropPlacesTokenAtBottom() {
        ConnectFourBoard board = new ConnectFourBoard();
        assertEquals(5, board.drop(0, ConnectFourBoard.P1));
        assertEquals(ConnectFourBoard.P1, board.get(5, 0));
    }

    @Test
    public void testVerticalWin() {
        ConnectFourBoard board = new ConnectFourBoard();
        for (int i = 0; i < 4; i++) board.drop(0, ConnectFourBoard.P1);
        assertTrue(board.hasWon(ConnectFourBoard.P1));
    }
    @Test
    public void testHorizontalWin() {
        ConnectFourBoard board = new ConnectFourBoard();

        for (int col = 0; col < 4; col++) {
            board.drop(col, ConnectFourBoard.P1);
        }

        assertTrue(board.hasWon(ConnectFourBoard.P1));
    }

    @Test
    public void testDiagonalWin() {
        ConnectFourBoard board = new ConnectFourBoard();

        // Build diagonal manually
        board.drop(0, ConnectFourBoard.P1);

        board.drop(1, ConnectFourBoard.P2);
        board.drop(1, ConnectFourBoard.P1);

        board.drop(2, ConnectFourBoard.P2);
        board.drop(2, ConnectFourBoard.P2);
        board.drop(2, ConnectFourBoard.P1);

        board.drop(3, ConnectFourBoard.P2);
        board.drop(3, ConnectFourBoard.P2);
        board.drop(3, ConnectFourBoard.P2);
        board.drop(3, ConnectFourBoard.P1);

        assertTrue(board.hasWon(ConnectFourBoard.P1));
    }

    @Test
    public void testFullColumn() {
        ConnectFourBoard board = new ConnectFourBoard();

        // Fill column
        for (int i = 0; i < 6; i++) {
            board.drop(0, ConnectFourBoard.P1);
        }

        // Try dropping again
        int result = board.drop(0, ConnectFourBoard.P1);

        assertEquals(-1, result); // invalid move
    }

    @Test
    public void testInvalidColumn() {
        ConnectFourBoard board = new ConnectFourBoard();

        assertEquals(-1, board.drop(-1, ConnectFourBoard.P1));
        assertEquals(-1, board.drop(7, ConnectFourBoard.P1));
    }

    @Test
    public void testLandingRow() {
        ConnectFourBoard board = new ConnectFourBoard();

        assertEquals(5, board.landingRow(0));

        board.drop(0, ConnectFourBoard.P1);

        assertEquals(4, board.landingRow(0));
    }

    @Test
    public void testBoardInitiallyEmpty() {
        ConnectFourBoard board = new ConnectFourBoard();

        for (int r = 0; r < ConnectFourBoard.ROWS; r++) {
            for (int c = 0; c < ConnectFourBoard.COLS; c++) {
                assertEquals(ConnectFourBoard.EMPTY, board.get(r, c));
            }
        }
    }
}

