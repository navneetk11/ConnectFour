package ca.yorku.eecs3311.connect4.model;

/** Strategy interface for choosing a move. */
public interface Player {
    Move getMove(ConnectFour game);
    String getName();
}
