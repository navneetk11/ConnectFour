package ca.yorku.eecs3311.connect4.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayerRandom implements Player {
    private final Random rng = new Random();

    @Override
    public Move getMove(ConnectFour game) {
        List<Integer> legal = this.legalColumns(game);
        if (legal.isEmpty()) return null;
        return new Move(legal.get(this.rng.nextInt(legal.size())));
    }

    protected List<Integer> legalColumns(ConnectFour game) {
        List<Integer> cols = new ArrayList<Integer>();
        for (int c = 0; c < ConnectFourBoard.COLS; c++) {
            if (game.getBoard().columnHasSpace(c)) cols.add(c);
        }
        return cols;
    }

    @Override
    public String getName() {
        return "Random";
    }
}
