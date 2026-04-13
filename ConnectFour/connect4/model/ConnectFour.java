package ca.yorku.eecs3311.connect4.model;

import java.io.*;

import ca.yorku.eecs3311.util.Observable;

/**
 * ConnectFour - Model class in MVC architecture.
 *
 * Responsibilities:
 * - Maintains game state (board, current player, number of moves)
 * - Handles game logic (moves, turn switching, win/draw detection)
 * - Notifies observers (views) when state changes
 *
 * MVC:
 * - This class acts as the Model.
 * - It does NOT contain any JavaFX/UI code.
 * - It communicates with the View via the Observer pattern.
 *
 * Command Pattern:
 * - Moves are executed using command objects (DropTokenCommand).
 * - CommandManager manages execution and supports undo/redo.
 *
 * Observer Pattern:
 * - Extends Observable.
 * - Notifies BoardView and StatusView when the game state changes.
 *
 * Additional Features:
 *
 * - Supports saving and loading game state
 */
public class ConnectFour extends Observable {
    private ConnectFourBoard board;
    private char whosTurn;
    private int numMoves;
    private Player player1;
    private Player player2;
    private int playerXScore = 0;
    private int playerOScore = 0;

    // completed A2-16:

    //  added move history, last move and show scores
    
    
    private CommandManager commandManager = new CommandManager();   // Command pattern: handles move execution
    private int lastColumn = -1;                                   // Tracks last move column
    private int lastRow = -1; //tracks last row
    public ConnectFour() {
        this.board = new ConnectFourBoard();
        this.whosTurn = ConnectFourBoard.P1;
        this.numMoves = 0;
    }

    public ConnectFourBoard getBoard() {
        return this.board;
    }

    public char getWhosTurn() {
        return this.whosTurn;
    }

    public int getNumMoves() {
        return this.numMoves;
    }

    public char getToken(int row, int col) {
        return this.board.get(row, col);
    }
    public int getLastColumn() {
        return this.lastColumn;
    }
    
    public int getLastRow() {
        return lastRow;
    }
  //getters for score update 
    public int getPlayerXScore() {
        return playerXScore;
    }

    public int getPlayerOScore() {
        return playerOScore;
    }

   
    /**
     * Executes a move using the Command pattern.
     *
     * Previously, move logic was implemented directly in this method.
     * It has now been refactored to use a command object (DropTokenCommand),
     * which is executed through CommandManager.
     *
     * This design allows:
     * - Undo/Redo functionality by storing move history
     * - Better separation of concerns (MVC)
     *
     * @param col the column where the token is dropped
     * @return true if the move is successful, false otherwise
     */
    public boolean move(int col) {
        if (this.isGameOver()) return false;

        GameCommand cmd = new DropTokenCommand(this, col);       // Create command object
        boolean success = this.commandManager.execute(cmd);     // Execute command via CommandManager (handles history for undo/redo)


        return success;
    }
    
    /**
     * Internal method that performs the actual token drop
     * This method is called by the command object.
     *
     * @param col the column where the token is dropped
     * @return true if move is successful, false otherwise
     */
    
    public boolean dropTokenInternal(int col) {
    	this.lastColumn = col;
        int row = this.board.drop(col, this.whosTurn);    // Drop token into board
       
        this.lastRow = row; //will be using for animation ; stores last row and col
        this.lastColumn = col;
        if (row < 0) return false;

        this.numMoves++;

        if (!this.isGameOver()) {                         //Switch turn only if game is not over
            this.whosTurn = this.board.otherPlayer(this.whosTurn);
        }
        
        //added for score update 
        if (this.board.hasWon(ConnectFourBoard.P1)) {
            playerXScore++;
        } else if (this.board.hasWon(ConnectFourBoard.P2)) {
            playerOScore++;
        }
        
        this.notifyObservers();                                  //Notify observers to update UI (Observer pattern)
        return true;
    }

    /**
     * Remove the topmost token from a column.
     *
     * This is enough for the starter command demo, but students may redesign
     * undo/redo more cleanly using explicit history objects or snapshots.
     */
    public boolean undoLastTokenInColumn(int col) {
        if (!this.board.validColumn(col)) return false;
        for (int r = 0; r < ConnectFourBoard.ROWS; r++) {
            if (this.board.get(r, col) != ConnectFourBoard.EMPTY) {
                this.board.set(r, col, ConnectFourBoard.EMPTY);
                if (this.numMoves > 0) this.numMoves--;
                this.whosTurn = this.board.otherPlayer(this.whosTurn);
                this.notifyObservers();
                return true;
            }
        }
        return false;
    }

    public void reset() {
        this.board.clear();
        this.whosTurn = ConnectFourBoard.P1;
        this.numMoves = 0;
        this.notifyObservers();
    }

    public boolean isGameOver() {
        return this.board.getWinner() != ConnectFourBoard.EMPTY
        || this.numMoves == ConnectFourBoard.ROWS * ConnectFourBoard.COLS;
    }

    public char getWinner() {
        return this.board.getWinner();
    }

    public String getStatusMessage() {
        char winner = this.getWinner();
        if (winner == ConnectFourBoard.P1) return "Player X wins";
        if (winner == ConnectFourBoard.P2) return "Player O wins";
        if (winner == ConnectFourBoard.DRAW) return "Draw";
        return "Player " + this.whosTurn + " to move";
    }

    public ConnectFour copy() {
        ConnectFour copy = new ConnectFour();
        copy.board = this.board.copy();
        copy.whosTurn = this.whosTurn;
        copy.numMoves = this.numMoves;
        return copy;
    }
    
    //added more model states 
    
    public void decrementMoveCount() {
        if (this.numMoves > 0) this.numMoves--;
    }
    
    public void switchTurnBack() {
        this.whosTurn = this.board.otherPlayer(this.whosTurn);
    }
    
    // completed A2-17:
    // Add save/load methods or move this responsibility to separate classes.

    /**
     * Saves the current game state to a file
     * Includes board configuration, current player, and move count
     *
     * @param filename the file to save the game state
     */
    
    public void saveGame(String filename) {
    	
    	//Open file for writing using BufferedWriter 
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {

            // Save current Player's turn
            writer.write(this.whosTurn);
            writer.newLine();

            // Save number of moves
            writer.write(String.valueOf(this.numMoves));
            writer.newLine();

            // Save board
            for (int r = 0; r < ConnectFourBoard.ROWS; r++) {
                for (int c = 0; c < ConnectFourBoard.COLS; c++) {
                    writer.write(this.board.get(r, c));
                }
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Loads game state from a file
     * Restores board configuration, current player, and move count
     *
     * @param filename the file containing saved game state
     */
    
    public void loadGame(String filename) {
    	
    	//Open file for reading using BufferedReader
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {

            // Load whose turn
            this.whosTurn = (char) reader.read();
            reader.readLine(); // move to next line

            // Load number of moves
            this.numMoves = Integer.parseInt(reader.readLine());

            // Load board
            for (int r = 0; r < ConnectFourBoard.ROWS; r++) {
                String line = reader.readLine();
                for (int c = 0; c < ConnectFourBoard.COLS; c++) {
                    this.board.set(r, c, line.charAt(c));
                }
            }

            this.notifyObservers();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setPlayers(Player p1, Player p2) {
        this.player1 = p1;
        this.player2 = p2;
    }
    
    public Player getCurrentPlayerStrategy() {
        if (this.whosTurn == ConnectFourBoard.P1) return player1;
        return player2;
    }

}
