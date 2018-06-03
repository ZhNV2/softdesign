package ru.spbau.zhidkov.model.item;

import ru.spbau.zhidkov.model.characteristic.CharacteristicName;
import ru.spbau.zhidkov.model.characteristic.CharacteristicsSet;
import ru.spbau.zhidkov.model.terrain.TerrainUnit;

/**
 * Interface for items (characteristics boosters) presented in game
 */
public interface Item extends TerrainUnit {

    void onEquip(CharacteristicsSet characteristicsSet);

    void unEquip(CharacteristicsSet characteristicsSet);

    int getId();

    String getName();

    String getUpdateDesc(CharacteristicName characteristic);

    @Override
    default Type getType() {
        return Type.ITEM;
    }
}
