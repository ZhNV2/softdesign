package ru.spbau.zhidkov.model.item;

import static ru.spbau.zhidkov.model.characteristic.CharacteristicName.ATTACK;

/**
 * Item that boosts only attack characteristics
 */
public class Sword extends OneCharacteristicBooster {

    private static final String NAME = "sword";

    public Sword(int id, int attackAddition) {
        super(ATTACK, id, attackAddition);
    }

    @Override
    public String getName() {
        return NAME;
    }

}
