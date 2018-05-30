package ru.spbau.zhidkov.model.characteristic;

import org.junit.Test;
import ru.spbau.zhidkov.model.characteristic.update.CharacteristicIncStrategy;
import ru.spbau.zhidkov.model.characteristic.update.CharacteristicUpdateStrategy;

import static org.junit.Assert.assertEquals;

public class CharacteristicsSetTest {

    @Test
    public void testMultiplyByTwoStrategy() {
        final CharacteristicUpdateStrategy multiplyByTwoStrategy = new CharacteristicUpdateStrategy() {
            @Override
            public int computeNewValue(int oldValue) {
                return oldValue * 2;
            }

            @Override
            public String desc() {
                return null;
            }

            @Override
            public CharacteristicUpdateStrategy negative() {
                return this;
            }
        };
        final CharacteristicsSet characteristicsSet = new CharacteristicsSet(2, 6, 10);
        characteristicsSet.update(CharacteristicName.HEALTH, multiplyByTwoStrategy);
        assertEquals(2, characteristicsSet.getValue(CharacteristicName.ATTACK));
        assertEquals(6, characteristicsSet.getValue(CharacteristicName.DEFENSE));
        assertEquals(20, characteristicsSet.getValue(CharacteristicName.HEALTH));
    }

    @Test
    public void testNegativeStrategy() {
        final CharacteristicUpdateStrategy inc10 = new CharacteristicIncStrategy(10);
        final CharacteristicsSet characteristicsSet = new CharacteristicsSet(2, 8, 15);
        characteristicsSet.update(CharacteristicName.ATTACK, inc10);
        characteristicsSet.update(CharacteristicName.ATTACK, inc10.negative());
        assertEquals(2, characteristicsSet.getValue(CharacteristicName.ATTACK));

    }
}
