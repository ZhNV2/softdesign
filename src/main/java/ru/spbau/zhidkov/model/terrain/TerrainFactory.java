package ru.spbau.zhidkov.model.terrain;

import org.lwjgl.Sys;
import ru.spbau.zhidkov.model.creature.MobFactory;
import ru.spbau.zhidkov.model.utils.Random;

import java.util.Scanner;

public class TerrainFactory {

    private final static Random random = new Random();
    private final static TerrainFactory INSTANCE = new TerrainFactory();

    private TerrainFactory() {
    }

    public static TerrainFactory getINSTANCE() {
        return INSTANCE;
    }

    public Terrain produceRandomTerrain(int rowsCnt, int columnsCnt, int maxStones, int maxSkeletons) {
        final Terrain terrain = new Terrain(rowsCnt, columnsCnt);
        final int stonesCnt = random.rand(0, maxStones);
        for (int i = 0; i < stonesCnt; i++) {
            terrain.addStoneRandomly();
        }
        final int skeletonsCnt = random.rand(0, maxSkeletons);
        for (int i = 0; i < skeletonsCnt; i++) {
            terrain.addCreatureRandomly(MobFactory.getINSTANCE().produceRandomSkeleton());
        }
        return terrain;
    }


}
