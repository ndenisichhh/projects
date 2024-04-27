package game;

public interface Board {
    Result makeMove(Move move);
    Position getPosition();
    int getMovesForLastPlayer();
    void setMovesForLastPlayer(int movesForLastPlayer);
}
