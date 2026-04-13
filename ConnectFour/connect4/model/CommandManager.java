package ca.yorku.eecs3311.connect4.model;

import java.util.Stack;

/**
 * CommandManager handles execution of commands and supports undo/redo functionality.
 *
 * Command Pattern:
 * -Each move is represented as a GameCommand object
 * - Commands are stored in stacks to support undo and redo operations
 *
 *Responsibilities:
 * - Execute commands
 * - Maintain history of executed commands (undo stack)
 * - Maintain redo history (redo stack)
 * - Provide undo and redo operations
 *
 */


public class CommandManager {
	
	// Stack storing executed commands for undo and redo
	
    private final Stack<GameCommand> undoStack = new Stack<GameCommand>();
    private final Stack<GameCommand> redoStack = new Stack<GameCommand>();

    
    /**
     * Executes a command and stores it in the undo history.
     * Clears redo history when a new command is executed.
     *
     * @param command the command to execute
     * @return true if execution is successful, false otherwise
     */
    
    public boolean execute(GameCommand command) {
        boolean ok = command.execute();
        if (ok) {
            this.undoStack.push(command);
            this.redoStack.clear();
        }
        return ok;
    }

    /**
     * Checks if undo operation is possible.
     *
     * @return true if undo stack is not empty
     */ 
    
    public boolean canUndo() {
        return !this.undoStack.isEmpty();
    }


    /**
     * Checks if redo operation is possible.
     *
     * @return true if redo stack is not empty
     */
    
    public boolean canRedo() {
        return !this.redoStack.isEmpty();
    }

    /**
     * Undoes the last executed command.
     * Moves the command to redo stack if successful.
     *
     * @return true if undo is successful, false otherwise
     */
    
    public boolean undo() {
        if (!this.canUndo()) return false;
        GameCommand command = this.undoStack.pop();
        boolean ok = command.undo();
        if (ok) this.redoStack.push(command);
        return ok;
    }

    /**
     * Redoes the last executed command.
     * Moves the command to undo stack if successful.
     *
     * @return true if undo is successful, false otherwise
     */

    public boolean redo() {
        if (!this.canRedo()) return false;
        GameCommand command = this.redoStack.pop();
        boolean ok = command.execute();
        if (ok) this.undoStack.push(command);
        return ok;
    }

    /**
     * Clears all command history.
     * Used when restarting the game.
     */
    
    public void clear() {
        this.undoStack.clear();
        this.redoStack.clear();
    }

    /**
     * Returns the number of commands in undo history.
     * Useful for disabling the Undo button when the stack is empty
     */
    public int getUndoSize() {
        return this.undoStack.size();
    }

    /**
     * Returns the number of commands in redo history
     * Useful for disabling the Redo button when the stack is empty
     */
    public int getRedoSize() {
        return this.redoStack.size();
    }
}
    
 // completed A2-18: 
 // CommandManager manages command history (undo/redo) using stacks.
 // Provides clean separation of command execution from UI logic.

