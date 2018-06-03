package ru.spbau.zhidkov.model.item;

import ru.spbau.zhidkov.model.characteristic.CharacteristicName;
import ru.spbau.zhidkov.model.characteristic.CharacteristicsSet;
import ru.spbau.zhidkov.model.characteristic.update.CharacteristicIncStrategy;
import ru.spbau.zhidkov.model.characteristic.update.CharacteristicUpdateStrategy;

/**
 * Abstract class for items that boosts only one characteristic
 */
abstract public class OneCharacteristicBooster implements Item {

    private final CharacteristicName characteristic;
    private final int id;
    private final CharacteristicUpdateStrategy incStrategy;

    public OneCharacteristicBooster(CharacteristicName characteristic, int id, int addition) {
        this.characteristic = characteristic;
        this.id = id;
        this.incStrategy = new CharacteristicIncStrategy(addition);
    }


    @Override
    public void onEquip(CharacteristicsSet characteristicsSet) {
        characteristicsSet.update(characteristic, incStrategy);
    }

    @Override
    public void unEquip(CharacteristicsSet characteristicsSet) {
        characteristicsSet.update(characteristic, incStrategy.negative());
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getUpdateDesc(CharacteristicName givenCharacteristic) {
        return characteristic == givenCharacteristic ? incStrategy.desc() : "";
    }



}
