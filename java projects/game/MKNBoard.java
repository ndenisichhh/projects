package game;

import java.util.Arrays;

public class MKNBoard implements Board, Position{
    private final Cell[][] field;
    private int countE;
    private final int m;
    private final int n;
    private final int k;
    private Cell turn;
    private int movesForLastPlayer = 0;
    public MKNBoard(int m, int n, int k) {
        this.m = m;
        this.n = n;
        this.k = k;
        field = new Cell[m][n];
        for (int i = 0; i < m; i++) Arrays.fill(field[i], Cell.E);
        countE = m*n;
        turn = Cell.X;
    }
    public int getM() {
        return m;
    }
    public int getN() {
        return n;
    }
    public int getMovesForLastPlayer() {
        return movesForLastPlayer;
    }
    public void setMovesForLastPlayer(int movesForLastPlayer) {
        this.movesForLastPlayer = movesForLastPlayer;
    }
    @Override
    public Result makeMove(Move move) {
        if (!isValid(move)) {
            System.out.println("Вы сделали неправильный ход, тем самым отдав победу другому игроку.");
            System.out.println("Впредь будьте внимательны.");
            System.out.println();
            return Result.LOSE;
        }
        final int row = move.getRow();
        final int col = move.getCol();
        final Cell cell = move.getCell();
        field[row][col] = cell;
        boolean result = checkMove(0, 1, move) ||
                checkMove(-1, 0, move) ||
                checkMove(-1, 1, move) ||
                checkMove(-1, -1, move);
        if (--countE == 0 && !result) return Result.DRAW;
        if (movesForLastPlayer == 0) turn = (turn == Cell.X) ? Cell.O : Cell.X;
        return result ? Result.WIN : Result.UNKNOWN;
    }
    private boolean checkMove(int stepDown, int stepRight, Move move) {
        int count = 1;

        int row = move.getRow();
        int col = move.getCol();
        Cell cell = move.getCell();

        int i = stepDown;
        int j = stepRight;
        while (row+i<m && col+j<n && 0<=row+i && 0<=col+j){
            if (field[row+i][col+j] == cell) {
                count++;
                i+=stepDown;
                j+=stepRight;
            } else break;
        }
        i = stepDown;
        j = stepRight;
        while (row-i<m && col-j<n && 0<=row-i && 0<=col-j){
            if (field[row-i][col-j] == cell) {
                count++;
                i+=stepDown;
                j+=stepRight;
            } else break;
        }
        if (count>=4) movesForLastPlayer+=1;
        return count >= k;
    }
    @Override
    public Cell getTurn() {
        return turn;
    }
    public boolean isValid(Move move) {
        final int row = move.getRow();
        final int col = move.getCol();
        return 0 <= row && row < m &&
                0 <= col && col < n &&
                field[row][col] == Cell.E &&
                move.getCell() == turn;
    }
    @Override
    public Position getPosition() {
        return this;
    }

    @Override
    public String toString(){
        final StringBuilder sb = new StringBuilder(" |");
        for (int i = 0; i < n; i++) sb.append(" ").append(i + 1);
        sb.append(System.lineSeparator());
        sb.append("-+");
        sb.append("--".repeat(n));
        sb.append(System.lineSeparator());
        for (int r = 0; r < m; r++){
            sb.append((r + 1)).append("| ");
            for (Cell cell : field[r]){
                sb.append(Cell.SYMBOLS.get(cell)).append(" ");
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
