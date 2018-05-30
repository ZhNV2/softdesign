package ru.spbau.zhidkov.model.characteristic;

import ru.spbau.zhidkov.model.characteristic.update.CharacteristicUpdateStrategy;

public class CharacteristicImpl implements Characteristic {

    private int value;

    public CharacteristicImpl(int value) {
        this.value = value;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void updateValue(CharacteristicUpdateStrategy updateStrategy) {
        value = updateStrategy.computeNewValue(value);
    }
}
