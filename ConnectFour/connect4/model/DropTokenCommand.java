package ca.yorku.eecs3311.connect4.model;

/**
 * DropTokenCommand represents a move in the game where a token
 * is dropped into a specific column.
 *
 * Command Pattern:
 * - Encapsulates a move as an object
 * - Allows execution, undo, and redo of moves
 *
 * A2-19 completed:
 * - Stores landing row of the token for precise undo
 */
public class DropTokenCommand implements GameCommand {

    private final ConnectFour game;
    private final int column;

    
    private int landingRow = -1;      // store landing row

    public DropTokenCommand(ConnectFour game, int column) {
        this.game = game;
        this.column = column;
    }

    /**
     * Executes the command.
     * Stores the row where the token lands.
     */
    @Override
    public boolean execute() {
        
        this.landingRow = game.getBoard().landingRow(column);   // Ask board where token will land before placing

        if (this.landingRow < 0) return false;

        
        return this.game.dropTokenInternal(column);          // Perform move
    }

    /**
     * Undoes the command.
     * Removes token from exact row instead of guessing.
     */
    @Override
    public boolean undo() {
        if (landingRow < 0) return false;

       
        game.getBoard().set(landingRow, column, ConnectFourBoard.EMPTY);    // Remove exact token

        // Fix model state
        game.decrementMoveCount();
        game.switchTurnBack();

        game.notifyObservers();
        return true;
    }


    @Override
    public String getName() {
        return "DropToken(" + this.column + ")";
    }

    // Completed A2-19:
    // Consider whether undo should restore a full snapshot or explicit row/token
    // information rather than just removing from the same column.
   
}
