package ca.yorku.eecs3311.connect4.viewcontroller;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

/**
 * One clickable cell in the Connect Four board view.
 *
 * Students may redesign this completely. For example, a cell could become a
 * StackPane containing a Circle, image, label, etc.
 */
public class CellView extends Button {
    private final int row;
    private final int col;

    public CellView(int row, int col) {
        this.row = row;
        this.col = col;
        this.setMinSize(60, 60);
        this.setPrefSize(60, 60);
        this.setFocusTraversable(false);

        // completed A2-10:
        // Add style classes, graphics, tooltips, hover behaviour, etc.
        
     // Base shape (circle)
        this.setStyle(
        		"-fx-border-color: black; "
        		+ "-fx-background-radius: 30;"+
                "-fx-border-radius: 30;"	
        				);
        
        // Tooltip 
        this.setTooltip(new Tooltip("Column " + col));

        //hover  behavior 
        this.setOnMouseEntered(e -> {
            this.setStyle(this.getStyle() + "; -fx-border-color: blue;");
        });

        this.setOnMouseExited(e -> {
            this.setStyle(this.getStyle().replace("-fx-border-color: blue;", "-fx-border-color: black;"));
        });
    }
    

    public int getRowIndex() {
        return this.row;
    }

    public int getColIndex() {
        return this.col;
    }
}
