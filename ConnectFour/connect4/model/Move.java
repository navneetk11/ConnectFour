package ca.yorku.eecs3311.connect4.model;

/** A Connect Four move is simply the choice of a column. */
public class Move {
    private final int column;

    public Move(int column) {
        this.column = column;
    }

    public int getColumn() {
        return this.column;
    }

    @Override
    public String toString() {
        return "(col=" + this.column + ")";
    }
}
