package game;

import java.util.Map;

public enum Cell {
    X, O, E;
    static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.'
    );
}
