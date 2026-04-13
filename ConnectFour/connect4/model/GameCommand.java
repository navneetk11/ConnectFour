package ca.yorku.eecs3311.connect4.model;

/**
 * Skeleton for command-based undo/redo.
 *
 * Students can extend this to implement undo/redo cleanly as requested in the handout.
 */
public interface GameCommand {
    boolean execute();
    boolean undo();
    String getName();
}
