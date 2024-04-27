package game;

import java.util.Random;

public class RandomPlayer implements Player{
    final Random random = new Random();

    @Override
    public Move makeMove(Position position) {
        while (true) {
            int row = random.nextInt(position.getM());
            int col = random.nextInt(position.getN());
            Move move = new Move(row, col, position.getTurn());
            if (position.isValid(move)) return move;
        }
    }
}
