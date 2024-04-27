package game;

public class CheatingPlayer implements Player{

    @Override
    public Move makeMove(Position position) {
        final Board board = (Board) position;
        return null;
    }
}
