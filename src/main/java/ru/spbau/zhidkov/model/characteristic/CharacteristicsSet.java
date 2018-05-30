package ru.spbau.zhidkov.model.characteristic;

import ru.spbau.zhidkov.model.characteristic.update.CharacteristicUpdateStrategy;

public class CharacteristicsSet implements CharacteristicsSetView {

    private final CharacteristicsMap<Integer> characteristicsToValue;

    public CharacteristicsSet(int initialAttack, int initialDefense, int initialHealth) {
        this.characteristicsToValue = new CharacteristicsMap<>(initialAttack, initialDefense, initialHealth);
    }

    public void update(CharacteristicName characteristic, CharacteristicUpdateStrategy updateStrategy) {
        final int oldValue = characteristicsToValue.get(characteristic);
        final int newValue = updateStrategy.computeNewValue(oldValue);
        characteristicsToValue.put(characteristic, newValue);
    }

    public int getValue(CharacteristicName characteristic) {
        return characteristicsToValue.get(characteristic);
    }

}
