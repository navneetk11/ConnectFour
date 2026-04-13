package ca.yorku.eecs3311.connect4.model;

/**
 * Model the state of a standard 6x7 Connect Four board.
 *
 * Students are expected to extend / refactor this class as needed for A2.
 */
public class ConnectFourBoard {
    public static final char EMPTY = ' ';
    public static final char P1 = 'X';
    public static final char P2 = 'O';
    public static final char DRAW = 'D';

    public static final int ROWS = 6;
    public static final int COLS = 7;

    private final char[][] board;

    public ConnectFourBoard() {
        this.board = new char[ROWS][COLS];
        this.clear();
    }

    public void clear() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                this.board[r][c] = EMPTY;
            }
        }
    }

    public ConnectFourBoard copy() {
        ConnectFourBoard copy = new ConnectFourBoard();
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                copy.board[r][c] = this.board[r][c];
            }
        }
        return copy;
    }

    public char get(int row, int col) {
        if (!this.validCoordinate(row, col)) return EMPTY;
        return this.board[row][col];
    }

    public char otherPlayer(char player) {
        if (player == P1) return P2;
        if (player == P2) return P1;
        return EMPTY;
    }

    public boolean validColumn(int col) {
        return 0 <= col && col < COLS;
    }

    public boolean validCoordinate(int row, int col) {
        return 0 <= row && row < ROWS && 0 <= col && col < COLS;
    }

    public boolean columnHasSpace(int col) {
        return this.validColumn(col) && this.board[0][col] == EMPTY;
    }

    public int landingRow(int col) {
        if (!this.columnHasSpace(col)) return -1;
        for (int r = ROWS - 1; r >= 0; r--) {
            if (this.board[r][col] == EMPTY) return r;
        }
        return -1;
    }

    public int drop(int col, char player) {
        int row = this.landingRow(col);
        if (row < 0) return -1;
        this.board[row][col] = player;
        return row;
    }

    public void set(int row, int col, char token) {
        if (this.validCoordinate(row, col)) {
            this.board[row][col] = token;
        }
    }

    public int getCount(char player) {
        int total = 0;
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (this.board[r][c] == player) total++;
            }
        }
        return total;
    }

    public boolean hasMove() {
        for (int c = 0; c < COLS; c++) {
            if (this.columnHasSpace(c)) return true;
        }
        return false;
    }

    private boolean hasLineFrom(int row, int col, int drow, int dcol, char player) {
        for (int k = 0; k < 4; k++) {
            int rr = row + k * drow;
            int cc = col + k * dcol;
            if (!this.validCoordinate(rr, cc) || this.board[rr][cc] != player) return false;
        }
        return true;
    }

    public boolean hasWon(char player) {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (this.board[r][c] != player) continue;
                if (this.hasLineFrom(r, c, 0, 1, player)) return true;
                if (this.hasLineFrom(r, c, 1, 0, player)) return true;
                if (this.hasLineFrom(r, c, 1, 1, player)) return true;
                if (this.hasLineFrom(r, c, 1, -1, player)) return true;
            }
        }
        return false;
    }

    public char getWinner() {
        if (this.hasWon(P1)) return P1;
        if (this.hasWon(P2)) return P2;
        if (!this.hasMove()) return DRAW;
        return EMPTY;
    }
    

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("  ");
        for (int c = 0; c < COLS; c++) sb.append(c).append(" ");
        sb.append("\n");
        for (int r = 0; r < ROWS; r++) {
            sb.append("|");
            for (int c = 0; c < COLS; c++) sb.append(this.board[r][c]).append("|");
            sb.append(" ").append(r).append("\n");
        }
        return sb.toString();
    }
    public boolean isPartOfWinningLine(int row, int col) {
        char player = this.get(row, col);
        if (player == EMPTY) return false;

        return checkDirection(row, col, 0, 1, player) ||   // horizontal
               checkDirection(row, col, 1, 0, player) ||   // vertical
               checkDirection(row, col, 1, 1, player) ||   // diagonal \
               checkDirection(row, col, 1, -1, player);    // diagonal /
    }

    private boolean checkDirection(int row, int col, int drow, int dcol, char player) {
        int count = 1;

        count += countDir(row, col, drow, dcol, player);
        count += countDir(row, col, -drow, -dcol, player);

        return count >= 4;
    }

    private int countDir(int row, int col, int drow, int dcol, char player) {
        int count = 0;
        int r = row + drow;
        int c = col + dcol;

        while (validCoordinate(r, c) && get(r, c) == player) {
            count++;
            r += drow;
            c += dcol;
        }
        return count;
    }
}
