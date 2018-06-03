package ru.spbau.zhidkov.model.terrain;

/**
 * Singleton for empty cell
 */
public class EmptyCell implements TerrainUnit {

    private final static EmptyCell INSTANCE = new EmptyCell();

    private EmptyCell() {};

    public static EmptyCell getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public Type getType() {
        return Type.EMPTY_CELL;
    }
}
