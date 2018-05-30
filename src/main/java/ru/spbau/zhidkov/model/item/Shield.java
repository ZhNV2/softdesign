package ru.spbau.zhidkov.model.item;

import ru.spbau.zhidkov.model.characteristic.CharacteristicName;
import ru.spbau.zhidkov.model.characteristic.CharacteristicsSet;
import ru.spbau.zhidkov.model.characteristic.update.CharacteristicIncStrategy;
import ru.spbau.zhidkov.model.characteristic.update.CharacteristicUpdateStrategy;

public class Shield extends OneCharacteristicBooster {

    private static final String NAME = "shield";

    public Shield(int id, int defenseAddition) {
        super(CharacteristicName.DEFENSE, id, defenseAddition);
    }

    @Override
    public String getName() {
        return NAME;
    }


}
