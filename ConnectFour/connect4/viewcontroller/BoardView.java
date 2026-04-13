package ca.yorku.eecs3311.connect4.viewcontroller;

import javafx.util.Duration;

import ca.yorku.eecs3311.connect4.model.ConnectFour;
import ca.yorku.eecs3311.connect4.model.ConnectFourBoard;
import ca.yorku.eecs3311.util.Observable;
import ca.yorku.eecs3311.util.Observer;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

/**
 * Visual representation of the board.
 *
 * This version is intentionally basic. It compiles and runs, but students are
 * expected to improve it substantially.
 */

/**
 * 
 *
 * MVC Role:
 * - Acts as the View component
 * - Observes the ConnectFour model and updates the UI when the game state changes
 *
 * Improvements made:
 * - Replaced basic text cells with styled circular tokens
 * - Added colors (red for Player X, yellow for Player O)
 * - Displayed "X" and "O" labels inside tokens for better readability
 * - Implemented last-move highlighting using a thicker blue border
 *
 * This improves usability, clarity, and overall GUI quality.
 */
public class BoardView extends GridPane implements Observer {
    private final CellView[][] cells = new CellView[ConnectFourBoard.ROWS][ConnectFourBoard.COLS];

    public BoardView() {
        this.setHgap(2);
        this.setVgap(2);
        this.setPadding(new Insets(10));
        this.setMaxWidth(GridPane.USE_PREF_SIZE);
        this.setMaxHeight(GridPane.USE_PREF_SIZE);
        this.setAlignment(javafx.geometry.Pos.CENTER);
        this.setStyle(
                "-fx-background-color: #1e3a5f;" +   // dark blue board
               
                "-fx-background-radius: 10;"
            );
        for (int r = 0; r < ConnectFourBoard.ROWS; r++) {
            for (int c = 0; c < ConnectFourBoard.COLS; c++) {
                CellView cell = new CellView(r, c);
                cell.setText(" ");
                this.cells[r][c] = cell;
                this.add(cell, c, r);
            }
        }

        // Completed A2-7:
        //Replaced basic text cells with styled circular tokens
       
    }

    public CellView getCell(int row, int col) {
        return this.cells[row][col];
    }

    @Override
    public void update(Observable observable) {
        ConnectFour game = (ConnectFour) observable;
        int lastCol = game.getLastColumn();
        for (int r = 0; r < ConnectFourBoard.ROWS; r++) {
            for (int c = 0; c < ConnectFourBoard.COLS; c++) {
            	CellView cell = this.cells[r][c];
                char token = game.getToken(r, c);
                //Animates the last row and column 
                int lastRow = game.getLastRow();
               

                if (r == lastRow && c == lastCol) {
                    ScaleTransition st = new ScaleTransition(Duration.millis(150), cell);
                    st.setFromX(0.5);
                    st.setFromY(0.5);
                    st.setToX(1.0);
                    st.setToY(1.0);
                    st.play();
                }
              

                // completed  A2-8:
                // Added colors (red for Player X, yellow for Player O)
                //Displayed "X" and "O" labels inside tokens
                
                boolean isLastMove = false;

                if (c == lastCol) {
                    // Find topmost token in this column
                    for (int rr = 0; rr < ConnectFourBoard.ROWS; rr++) {
                        if (game.getToken(rr, c) != ConnectFourBoard.EMPTY) {
                            if (rr == r) isLastMove = true;
                            break;
                        }
                    }
                }
                

                if (token == ConnectFourBoard.P1) {

                	cell.setText("X");

                	cell.setStyle(
                	    "-fx-background-color: red; " +
                	    "-fx-background-radius: 30; " +
                	    "-fx-border-color: " + (isLastMove ? "blue;" : "black;") +
                	    "-fx-border-width: " + (isLastMove ? "3;" : "1;")+
                	    "-fx-font-size: 18px; " +
                	    "-fx-font-weight: bold; " +
                	    "-fx-text-fill: white;"
                	);

                } else if (token == ConnectFourBoard.P2) {

                	cell.setText("O");

                	cell.setStyle(
                	    "-fx-background-color: yellow; " +
                	    "-fx-background-radius: 30; " +
                	    "-fx-border-color: " + (isLastMove ? "blue;" : "black;") +
                	    "-fx-border-width: " + (isLastMove ? "3;" : "1;")+
                	    "-fx-font-size: 18px; " +
                	    "-fx-font-weight: bold; " +
                	    "-fx-text-fill: black;"
                	);

                } else {

                    cell.setText(""); // empty
                    cell.setStyle(
                        "-fx-background-color: white; " +
                        "-fx-background-radius: 30; " +
                        "-fx-border-color: black; "
                    );
                }
                ConnectFourBoard board = game.getBoard();

                if (board.isPartOfWinningLine(r, c)) {
                    //Highlights winning cells
                    cell.setStyle("-fx-background-color: #2ecc71; -fx-background-radius: 30;");
                }
            }
            
           
        }

        // completed  A2-9:
        //changed the color of winning cells to green 
       
       
       
    }
}
