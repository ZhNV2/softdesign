package ru.spbau.zhidkov.model.terrain;

import ru.spbau.zhidkov.model.creature.MobFactory;
import ru.spbau.zhidkov.model.utils.Random;

public class TerrainFactory {

    private final static Random random = new Random();
    private final static TerrainFactory INSTANCE = new TerrainFactory();

    private TerrainFactory() {}

    public static TerrainFactory getINSTANCE() {
        return INSTANCE;
    }

    public Terrain produceRandomTerrain(int rowsCnt, int columnsCnt) {
        final Terrain terrain = new Terrain(rowsCnt, columnsCnt);
        final int stonesCnt = random.rand(0, rowsCnt * columnsCnt / 20);
        for (int i = 0; i < stonesCnt; i++) {
            terrain.addStoneRandomly();
        }
        terrain.addCreatureRandomly(MobFactory.getINSTANCE().produceRandomSkeleton());
        return terrain;
    }
}
