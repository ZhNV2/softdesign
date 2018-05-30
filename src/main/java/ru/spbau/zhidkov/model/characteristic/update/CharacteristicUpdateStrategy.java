package ru.spbau.zhidkov.model.characteristic.update;

public interface CharacteristicUpdateStrategy {

    int computeNewValue(int oldValue);

    String desc();

    CharacteristicUpdateStrategy negative();

}
