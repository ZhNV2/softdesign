package ru.spbau.zhidkov.model.terrain;

import org.codetome.zircon.api.Position;
import ru.spbau.zhidkov.model.creature.Creature;
import ru.spbau.zhidkov.model.creature.Player;
import ru.spbau.zhidkov.model.item.Item;
import ru.spbau.zhidkov.model.utils.Random;

public class Terrain implements TerrainView {

    private static final Random random = new Random();

    private final int rowsCnt;
    private final int columnsCnt;
    private final TerrainUnit[][] map;

    public Terrain(int rowsCnt, int columnsCnt) {
        this.rowsCnt = rowsCnt;
        this.columnsCnt = columnsCnt;
        this.map = new TerrainUnit[rowsCnt][columnsCnt];
        initMap(this.map);
    }

    public int getRowsCnt() {
        return rowsCnt;
    }

    public int getColumnsCnt() {
        return columnsCnt;
    }

    public void set(int row, int column, TerrainUnit unit) {
        map[row][column] = unit;
    }

    public TerrainUnit get(int row, int column) {
        return map[row][column];
    }

    public void addStoneRandomly() {
        final TerrainUnit stone = Stone.getINSTANCE();
        while (!tryAdd(random.rand(0, rowsCnt - 1), random.rand(0, columnsCnt - 1), stone));
    }

    public void addItemRandomly(Item item) {
        while (!tryAdd(random.rand(0, rowsCnt - 1), random.rand(0, columnsCnt - 1), item));
    }

    public void addCreatureRandomly(Creature creature) {
        while (!tryAdd(random.rand(0, rowsCnt - 1), random.rand(0, columnsCnt - 1), creature));
    }

    public Position addPlayerRandomly(Creature player) {
        while (true) {
            int row = random.rand(0, rowsCnt - 1);
            int col = random.rand(0, columnsCnt - 1);
            if (tryAdd(row, col, player)) {
                return new Position(col, row);
            }
        }
    }



    private boolean tryAdd(int i, int j, TerrainUnit unit) {
        if (get(i, j).getType() != TerrainUnit.Type.EMPTY_CELL) {
            return false;
        }
        set(i, j, unit);
        return true;
    }

    private void initMap(TerrainUnit[][] map) {
        for (int i = 0; i < rowsCnt; i++) {
            for (int j = 0; j < columnsCnt; j++) {
                if (i == 0 || j == 0 || i == rowsCnt - 1 || j == columnsCnt - 1) {
                    map[i][j] = Stone.getINSTANCE();
                } else {
                    map[i][j] = EmptyCell.getINSTANCE();
                }
            }
        }
    }
}
