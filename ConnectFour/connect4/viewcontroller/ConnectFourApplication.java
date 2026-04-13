package ca.yorku.eecs3311.connect4.viewcontroller;

import ca.yorku.eecs3311.connect4.model.CommandManager;
import ca.yorku.eecs3311.connect4.model.ConnectFour;
import ca.yorku.eecs3311.connect4.model.ConnectFourBoard;
import ca.yorku.eecs3311.connect4.model.OpponentFactory;
import ca.yorku.eecs3311.connect4.model.Player;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * JavaFX entry point for the assignment starter.
 *
 * IMPORTANT:
 * This file is intentionally a STARTER and not a finished solution.
 * Students should extend/refactor it using MVC and design patterns.
 */
public class ConnectFourApplication extends Application {
    // REMEMBER: To run this in the lab put
    // --module-path "/usr/share/openjfx/lib" --add-modules javafx.controls,javafx.fxml
    // in the run configuration under VM arguments.

    @Override
    public void start(Stage stage) throws Exception {
        // ================================================================
        // MODEL
        // ================================================================
        ConnectFour game = new ConnectFour();
        CommandManager commandManager = new CommandManager();

        // completed -  A2-1:
       
 
        //  Model objects are initialized here, while strategy selection
        // is delegated to OpponentFactory (Strategy pattern)
        
        // ================================================================
        // VIEW
        // ================================================================
        BoardView boardView = new BoardView();
        
        StatusView statusView = new StatusView();
        ControlPanel controlPanel = new ControlPanel();

        // completed -  A2-2:

        // GUI uses styled circular cells with colored tokens (X/O),
        // improving readability and usability
        

        // ================================================================
        // CONTROLLERS
        // ================================================================
        Player opponent = null; // Human vs Human by default.
        DropTokenEventHandler boardHandler = new DropTokenEventHandler(game, commandManager, opponent);

        // completed -A2-3:
     // A single controller (DropTokenEventHandler) handles board interactions,
     // while separate event handlers are defined for control panel actions
     // such as restart, undo/redo, save/load, and opponent selection.
        
        // ================================================================
        // VIEW -> CONTROLLER hookup
        // ================================================================
        for (int r = 0; r < ConnectFourBoard.ROWS; r++) {
            for (int c = 0; c < ConnectFourBoard.COLS; c++) {
                boardView.getCell(r, c).setOnAction(boardHandler);
            }
        }

        controlPanel.getRestartButton().setOnAction(e -> {
            commandManager.clear();
            game.reset();
        });

        controlPanel.getUndoButton().setOnAction(e -> commandManager.undo());
        controlPanel.getRedoButton().setOnAction(e -> commandManager.redo());
        
        // added for load and save 
        
        controlPanel.getSaveButton().setOnAction(e -> {
            game.saveGame("test.txt");
        });

        controlPanel.getLoadButton().setOnAction(e -> {
            game.loadGame("test.txt");
        });

        controlPanel.getOpponentChoice().setOnAction(e -> {
            String selected = controlPanel.getOpponentChoice().getValue();
            if ("Human".equalsIgnoreCase(selected)) {
                boardHandler.setOpponent(null);
            } else {
                boardHandler.setOpponent(OpponentFactory.create(selected, ConnectFourBoard.P2));
            }
        });

     // Completed A2-4:
     // Save and Load buttons have been added and connected to the model methods.
     // Undo/Redo and Restart are also implemented using CommandManager and model logic.
     // Opponent selection dynamically updates the strategy using OpponentFactory.
        
        // ================================================================
        // MODEL -> VIEW hookup
        // ================================================================
        game.attach(boardView);
        game.attach(statusView);
        game.notifyObservers();  //intial UI update
        
        // completed  A2-5:
        // Added Move history , number of moves and Scores for each player
        
        MoveHistoryView historyView = new MoveHistoryView();
        game.attach(historyView);
        
        // ================================================================
        // LAYOUT
        // ================================================================
        VBox top = new VBox();
        top.setPadding(new Insets(15));
        top.setSpacing(10);
        top.setStyle(
            "-fx-background-color: #ffffff;" +
            "-fx-border-color: #cccccc;" +
            "-fx-border-width: 0 0 2 0;"
        );
        top.getChildren().addAll(controlPanel, statusView,historyView);

        BorderPane root = new BorderPane();
        
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #e0f7fa, #b2ebf2);");
        root.setTop(top);
        root.setCenter(boardView);

        Scene scene = new Scene(root);
        
        stage.setTitle("Connect Four");
        stage.setScene(scene);
        
        
        
        stage.show();
        

    
     // completed A2-6:
     // Improved overall UI/UX of the application:
     // - Applied custom styling to the board (dark blue background, rounded corners)
     // - Designed circular tokens with colors (red for Player X, yellow for Player O)
     // - Highlighted the last move using a thicker blue border for better feedback
     // - Added and styled MoveHistoryView with proper size, padding, and readability
     // - Organized layout using VBox and BorderPane for better structure and spacing
     // - Applied background gradient to the main window for improved visual appeal
     // - Improved font sizes, alignment, and spacing for a cleaner interface
    
    }

    public static void main(String[] args) {
        launch(args);
    }
}
