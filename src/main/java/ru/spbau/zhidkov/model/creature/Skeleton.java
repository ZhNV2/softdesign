package ru.spbau.zhidkov.model.creature;

import org.codetome.zircon.api.Position;
import ru.spbau.zhidkov.model.characteristic.CharacteristicsSet;
import ru.spbau.zhidkov.model.item.Item;
import ru.spbau.zhidkov.model.utils.Random;
import ru.spbau.zhidkov.model.terrain.Movement;
import ru.spbau.zhidkov.model.terrain.Terrain;

/**
 * Class for the weakest mob - skeleton
 */
public class Skeleton extends Mob {

    private final static int INITIAL_DEFENSE = 0;
    private final static int INITIAL_HEALTH = 5;
    private final static Random random = new Random();

    public Skeleton(int initialAttack) {
        super(new CharacteristicsSet(initialAttack, INITIAL_DEFENSE, INITIAL_HEALTH));
    }

    @Override
    public Movement move(Position position, Terrain terrain) {
        return Movement.values()[random.rand(0, Movement.values().length - 1)];
    }

    @Override
    public void onDepth() {
    }

    @Override
    public void onItem(Item item) {
    }
}
