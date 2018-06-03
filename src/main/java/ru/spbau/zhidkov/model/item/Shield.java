package ru.spbau.zhidkov.model.item;

import ru.spbau.zhidkov.model.characteristic.CharacteristicName;

/**
 * Item that boosts only defense characteristics
 */
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
