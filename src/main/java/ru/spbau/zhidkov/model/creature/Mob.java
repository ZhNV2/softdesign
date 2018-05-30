package ru.spbau.zhidkov.model.creature;

import ru.spbau.zhidkov.model.characteristic.CharacteristicsSet;

abstract public class Mob implements Creature {

    private final CharacteristicsSet characteristicsSet;

    public Mob(CharacteristicsSet characteristicsSet) {
        this.characteristicsSet = characteristicsSet;
    }

    public CharacteristicsSet getCharacteristics() {
        return characteristicsSet;
    }



}
