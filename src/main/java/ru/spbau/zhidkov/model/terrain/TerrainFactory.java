package ru.spbau.zhidkov.model.terrain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.spbau.zhidkov.model.creature.MobFactory;
import ru.spbau.zhidkov.model.utils.Random;

public class TerrainFactory {

    private final static Logger LOG = LogManager.getLogger(TerrainFactory.class);

    private final static Random RANDOM = new Random();
    private final static TerrainFactory INSTANCE = new TerrainFactory();

    private TerrainFactory() {
    }

    public static TerrainFactory getINSTANCE() {
        return INSTANCE;
    }

    public Terrain produceRandomTerrain(int rowsCnt, int columnsCnt, int maxStones, int maxSkeletons) {
        LOG.debug("produce random terrain (" + rowsCnt + ", " + columnsCnt + ")");
        final Terrain terrain = new Terrain(rowsCnt, columnsCnt);
        final int stonesCnt = RANDOM.rand(0, maxStones);
        for (int i = 0; i < stonesCnt; i++) {
            terrain.addStoneRandomly();
        }
        final int skeletonsCnt = RANDOM.rand(0, maxSkeletons);
        for (int i = 0; i < skeletonsCnt; i++) {
            terrain.addCreatureRandomly(MobFactory.getINSTANCE().produceRandomSkeleton());
        }
        return terrain;
    }


}
