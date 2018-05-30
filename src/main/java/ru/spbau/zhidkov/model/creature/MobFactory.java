package ru.spbau.zhidkov.model.creature;


import ru.spbau.zhidkov.model.utils.Random;

public class MobFactory {

    private final static Random random = new Random();
    private final static MobFactory INSTANCE = new MobFactory();

    private MobFactory() {}

    public static MobFactory getINSTANCE() {
        return INSTANCE;
    }

    public Mob produceRandomSkeleton() {
        return new Skeleton(random.rand(1, 15));
    }
}
