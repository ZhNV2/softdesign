package ru.spbau.zhidkov.model.gameplay;

import ru.spbau.zhidkov.model.characteristic.CharacteristicName;
import ru.spbau.zhidkov.model.characteristic.update.CharacteristicIncStrategy;
import ru.spbau.zhidkov.model.creature.Creature;

public class Fight {

    private final Creature first;
    private final Creature second;

    public Fight(Creature first, Creature second) {
        this.first = first;
        this.second = second;
    }

    public Creature fight() {
        final Creature[] creatures = {first, second};
        while (true) {
            attack(creatures[0], creatures[1]);
            if (creatures[1].getCharacteristics().getValue(CharacteristicName.HEALTH) <= 0) {
                creatures[1].onDepth();
                return creatures[0];
            }
            Creature tmp = creatures[0];
            creatures[0] = creatures[1];
            creatures[1] = tmp;
        }
    }


    private void attack(Creature attacking, Creature attacked) {
        final int attack = Math.max(0, attacking.getCharacteristics().getValue(CharacteristicName.ATTACK) -
                attacked.getCharacteristics().getValue(CharacteristicName.DEFENSE));
        attacked.getCharacteristics().update(CharacteristicName.HEALTH, new CharacteristicIncStrategy(-attack));
    }


}
