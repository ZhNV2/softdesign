package ru.spbau.zhidkov.model.terrain;

public interface TerrainUnit {

    enum Type {
        EMPTY_CELL,
        STONE,
        ITEM,
        CREATURE
    }

    Type getType();
}
