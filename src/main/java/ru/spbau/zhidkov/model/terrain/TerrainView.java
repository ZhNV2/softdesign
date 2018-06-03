package ru.spbau.zhidkov.model.terrain;

/**
 * Interface presenting view (getters only) on game map
 */
public interface TerrainView {

    TerrainUnit get(int row, int column);

    int getRowsCnt();

    int getColumnsCnt();

}
