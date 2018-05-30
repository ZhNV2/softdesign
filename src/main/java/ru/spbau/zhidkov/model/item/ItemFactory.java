package ru.spbau.zhidkov.model.item;


import ru.spbau.zhidkov.model.utils.Random;

public class ItemFactory {

    private static final ItemFactory INSTANCE = new ItemFactory();
    private static final Random random = new Random();

    private ItemFactory() {}

    public static ItemFactory getINSTANCE() {
        return INSTANCE;
    }

    public Item produceOneCharacteristicBoosterRandomItem() {
        final int addition = random.rand(1, 10);
        final int id = random.rand(0, Integer.MAX_VALUE);
        if (random.rand(0, 1) == 0) {
            return new Sword(id, addition);
        } else {
            return new Shield(id, addition);
        }
    }

}
