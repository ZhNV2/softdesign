package ru.spbau.zhidkov.model.terrain;

/**
 * Interface for any unit that can be placed in game map cell
 */
public interface TerrainUnit {

    enum Type {
        EMPTY_CELL,
        STONE,
        ITEM,
        CREATURE
    }

    Type getType();
}
