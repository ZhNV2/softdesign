package ru.spbau.zhidkov.model.terrain;

public interface TerrainView {

    TerrainUnit get(int row, int column);

    int getRowsCnt();

    int getColumnsCnt();

}
