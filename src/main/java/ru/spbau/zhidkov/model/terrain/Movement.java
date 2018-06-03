package ru.spbau.zhidkov.model.terrain;


import org.codetome.zircon.api.Position;

/**
 * Enum for possible creature movements
 */
public enum Movement {

    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1),
    NO_MOVE(0, 0);

    private final int drow;
    private final int dcol;

    Movement(int drow, int dcol) {
        this.drow = drow;
        this.dcol = dcol;
    }

    public Position shift(Position position) {
        return new Position(position.getColumn() + dcol, position.getRow() + drow);
    }

}
