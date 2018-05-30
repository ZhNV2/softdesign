package ru.spbau.zhidkov.model.terrain;

public class Stone implements TerrainUnit {

    private static final Stone INSTANCE = new Stone();

    private Stone() {}

    public static Stone getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public Type getType() {
        return Type.STONE;
    }
}
