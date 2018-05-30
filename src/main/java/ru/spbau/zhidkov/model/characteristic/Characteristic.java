package ru.spbau.zhidkov.model.characteristic;

import ru.spbau.zhidkov.model.characteristic.update.CharacteristicUpdateStrategy;

public interface Characteristic {

    int getValue();

    void updateValue(CharacteristicUpdateStrategy updateStrategy);

}
