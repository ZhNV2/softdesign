package ru.spbau.zhidkov.model.item;

import ru.spbau.zhidkov.model.characteristic.CharacteristicName;
import ru.spbau.zhidkov.model.characteristic.CharacteristicsSet;
import ru.spbau.zhidkov.model.characteristic.update.CharacteristicIncStrategy;
import ru.spbau.zhidkov.model.characteristic.update.CharacteristicUpdateStrategy;

import static ru.spbau.zhidkov.model.characteristic.CharacteristicName.ATTACK;

class Sword extends OneCharacteristicBooster {

    private static final String NAME = "sword";

    Sword(int id, int attackAddition) {
        super(ATTACK, id, attackAddition);
    }

    @Override
    public String getName() {
        return NAME;
    }

}
