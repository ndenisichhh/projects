package game;

public class Game{

    private final Player player1;
    private final Player player2;
    private final boolean log;
    public Game(Player player1, Player player2, boolean log) {
        this.player1 = player1;
        this.player2 = player2;
        this.log = log;
    }
    public int play(Board board) {
        while (true) {
            if (log) System.out.println("Сейчас ходят " + Cell.X);
            int result1 = makeMove(player1, 1, board);
            if (log) System.out.println(board);
            if (result1 >= 0) return result1;

            if (log) System.out.println("Сейчас ходят " + Cell.O);
            int result2 = makeMove(player2, 2, board);
            if (log) System.out.println(board);
            if (result2 >= 0) return result2;
        }
    }
    private int makeMove(Player player, int no, Board board){
        try {
            Move move = player.makeMove(board.getPosition());
            if (log) System.out.println("Ход: " + move);
            Result result = board.makeMove(move);
            while (board.getMovesForLastPlayer() > 0 && (result == Result.UNKNOWN)){
                board.setMovesForLastPlayer(board.getMovesForLastPlayer()-1);
                if (log) {
                    System.out.println(board);
                    System.out.println("Сейчас ходят " + move.getCell());
                }
                move = player.makeMove(board.getPosition());
                if (log) System.out.println("Ход: " + move);
                result = board.makeMove(move);
            }
            return switch (result) {
                case WIN -> no;
                case LOSE -> 3 - no;
                case DRAW -> 0;
                case UNKNOWN -> -1;
            };
        } catch (RuntimeException e){
            return 3 - no;
        }
    }
}
