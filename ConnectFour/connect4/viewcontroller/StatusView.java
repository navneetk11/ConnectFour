package ca.yorku.eecs3311.connect4.viewcontroller;

import ca.yorku.eecs3311.connect4.model.ConnectFour;
import ca.yorku.eecs3311.util.Observable;
import ca.yorku.eecs3311.util.Observer;
import javafx.scene.control.Label;

/**
 * Tiny observer view showing the current status.
 */
public class StatusView extends Label implements Observer {
    @Override
    public void update(Observable observable) {
        ConnectFour game = (ConnectFour) observable;
      
        
     // completed A2-15:
        // Improved status area by:
        // - Displaying current game status (turn, win, draw)
        // - Showing total number of moves made
        // - Showing live score for Player X and Player O
        // - Applying styling (font, padding, background, colors)
        // - Highlighting game over state in red for better feedback

        // Get basic status message from model
        String status = game.getStatusMessage();

        // Improve text appearance
        String fullText =
        	      status + "\n" +
        	    "Moves: " + game.getNumMoves() + "\n" +
        	    "Score → X: " + game.getPlayerXScore() +
        	    "   O: " + game.getPlayerOScore();

            // Apply styling
        this.setWrapText(true);
        this.setStyle(
        	    "-fx-font-size: 14px;" +
        	    "-fx-font-weight: bold;" +
        	    "-fx-padding: 10;" +
        	    "-fx-background-color: #f1f5f9;" +
        	    "-fx-border-color: #d0d7de;" +
        	    "-fx-border-radius: 8;" +
        	    "-fx-background-radius: 8;" +
        	    "-fx-line-spacing: 4;"
        	);

            // Show color based on game state
            if (game.isGameOver()) {
                this.setStyle(this.getStyle() + "-fx-text-fill: red;");
                this.setText("Game Over\n" + fullText);
            } else {
                this.setStyle(this.getStyle() + "-fx-text-fill: #1d3557;");
                this.setText(fullText);
            }
        }
    }
    

