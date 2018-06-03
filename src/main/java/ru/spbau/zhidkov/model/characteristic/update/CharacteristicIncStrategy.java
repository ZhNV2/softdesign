package ru.spbau.zhidkov.model.characteristic.update;

/**
 * Class describing incremental characteristic update strategy
 */
public class CharacteristicIncStrategy implements CharacteristicUpdateStrategy {

    private final int addition;

    public CharacteristicIncStrategy(int addition) {
        this.addition = addition;
    }

    @Override
    public int computeNewValue(int oldValue) {
        return oldValue + addition;
    }


    @Override
    public String desc() {
        return "+" + String.valueOf(addition);
    }

    @Override
    public CharacteristicUpdateStrategy negative() {
        return new CharacteristicIncStrategy(-addition);
    }
}
