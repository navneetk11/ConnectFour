package ca.yorku.eecs3311.connect4.viewcontroller;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;

/**
 * Small collection of top-level controls for the GUI.
 */
public class ControlPanel extends HBox {
    private final ComboBox<String> opponentChoice = new ComboBox<String>();
    private final Button restartButton = new Button("Restart");
    private final Button undoButton = new Button("Undo");
    private final Button redoButton = new Button("Redo");
    private Button saveButton = new Button("Save");
    private Button loadButton = new Button("Load");

 // completed A2-11:
 // Added Save and Load buttons with getters
 // Improved control panel layout using spacing and padding
 // Applied consistent styling to all buttons (color, font, hover effect)
 // Enhanced UI/UX with a cleaner and more modern design
    public ControlPanel() {
        this.setSpacing(12);
        this.setPadding(new Insets(10));
        this.setStyle(
                "-fx-background-color: #ffffff;" +
                "-fx-border-color: #d0d7de;" +
                "-fx-border-radius: 8;" +
                "-fx-background-radius: 8;"
            );
        
        this.opponentChoice.getItems().addAll("Human", "Random", "Greedy","First","defensive");
        this.opponentChoice.getSelectionModel().select("Human");
     // Style all buttons
        styleButton(restartButton);
        styleButton(undoButton);
        styleButton(redoButton);
        styleButton(saveButton);
        styleButton(loadButton);
        //added load and save
        this.getChildren().addAll(this.opponentChoice, this.restartButton, this.undoButton, this.redoButton,this.loadButton,this.saveButton);
    }
    private void styleButton(Button button) {
        button.setStyle(
            "-fx-background-color: #1d3557;" +
            "-fx-text-fill: white;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 6;" +
            "-fx-padding: 6 12 6 12;"
        );

        // Hover effect
        button.setOnMouseEntered(e ->
            button.setStyle(
                "-fx-background-color: #457b9d;" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 6;" +
                "-fx-padding: 6 12 6 12;"
            )
        );

        button.setOnMouseExited(e ->
            button.setStyle(
                "-fx-background-color: #1d3557;" +
                "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" +
                "-fx-background-radius: 6;" +
                "-fx-padding: 6 12 6 12;"
            )
        );
    }
    public ComboBox<String> getOpponentChoice() {
        return this.opponentChoice;
    }

    public Button getRestartButton() {
        return this.restartButton;
    }

    public Button getUndoButton() {
        return this.undoButton;
    }

    public Button getRedoButton() {
        return this.redoButton;
    }
    public Button getSaveButton() {
        return saveButton;
    }

    public Button getLoadButton() {
        return loadButton;
    }
}
