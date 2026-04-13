package ca.yorku.eecs3311.connect4.viewcontroller;


import ca.yorku.eecs3311.connect4.model.ConnectFour;
import ca.yorku.eecs3311.util.Observable;
import ca.yorku.eecs3311.util.Observer;
import javafx.scene.control.TextArea;

/**
 * Displays move history of the game.
 *
 * Observer (MVC):
 * - Updates automatically when model changes
 */
public class MoveHistoryView extends TextArea implements Observer {

    public MoveHistoryView() {
        this.setEditable(false);
        this.setPrefRowCount(4);
        this.setPrefHeight(80);
        this.setMaxHeight(90);
        this.setWrapText(true);
        this.setStyle(
        	    "-fx-background-color: #ffffff;" +
        	    "-fx-border-color: #d0d7de;" +
        	    "-fx-border-radius: 8;" +
        	    "-fx-background-radius: 8;" +
        	    "-fx-font-size: 12px;" +
        	    "-fx-padding: 8;" +
        	    "-fx-control-inner-background: #ffffff;"
        	);
        this.setPromptText("Move History...");
        
    }

    @Override
    public void update(Observable observable) {
        ConnectFour game = (ConnectFour) observable;

        // Simple history using move count + last column
        int moves = game.getNumMoves();
        int lastCol = game.getLastColumn();

        if (lastCol >= 0) {
            char player = game.getWhosTurn() == 'X' ? 'O' : 'X';

            this.appendText(player + " → Column " + lastCol + "\n");
        }

        // Reset if game restarted
        if (moves == 0) {
            this.clear();
        }
    }
}
