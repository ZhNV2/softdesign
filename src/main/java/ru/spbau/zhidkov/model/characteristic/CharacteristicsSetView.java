package ru.spbau.zhidkov.model.characteristic;

/**
 * Class providing view (getters only) on characteristic set
 */
public interface CharacteristicsSetView {

    int getValue(CharacteristicName characteristic);
}
