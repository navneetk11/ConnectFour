package ca.yorku.eecs3311.connect4.viewcontroller;

import ca.yorku.eecs3311.connect4.model.CommandManager;
import ca.yorku.eecs3311.connect4.model.ConnectFour;
import ca.yorku.eecs3311.connect4.model.DropTokenCommand;
import ca.yorku.eecs3311.connect4.model.Move;
import ca.yorku.eecs3311.connect4.model.Player;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Controller for board-button presses.
 *
 * This class is deliberately simple so students can refactor toward a cleaner
 * MVC design.
 */
public class DropTokenEventHandler implements EventHandler<ActionEvent> {
    private final ConnectFour game;
    private final CommandManager commandManager;
    private Player opponent;

    public DropTokenEventHandler(ConnectFour game, CommandManager commandManager, Player opponent) {
        this.game = game;
        this.commandManager = commandManager;
        this.opponent = opponent;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    @Override
    public void handle(ActionEvent event) {
        if (!(event.getSource() instanceof CellView)) return;
        if (this.game.isGameOver()) return;

        CellView cell = (CellView) event.getSource();
        int selectedColumn = cell.getColIndex();

        // completed A2-12:
        // Clicking any cell in a column drops the token into that column.
        // This simplifies the UI since the user does not need to click only the top row.

        boolean moved = this.commandManager.execute(new DropTokenCommand(this.game, selectedColumn));
        if (moved) {
            this.maybeMakeComputerMove();
        }

        // completed A2-13:
        // Controller handles user interaction and game flow:
        // - Executes moves using Command pattern
        // - Triggers opponent move after player turn
        // - Prevents input when game is over
        // - Move history and UI updates are handled via Observer pattern
    }

    private void maybeMakeComputerMove() {
        if (this.opponent == null || this.game.isGameOver()) return;
        Move move = this.opponent.getMove(this.game);
        if (move != null && !this.game.isGameOver()) {
            this.commandManager.execute(new DropTokenCommand(this.game, move.getColumn()));
        }

     // completed A2-14:
     // Opponent move logic is handled separately from user interaction,
     // improving separation of concerns. The controller delegates move
     // selection to the opponent (Strategy pattern) and executes it
     // using the Command pattern. 
    }
}
