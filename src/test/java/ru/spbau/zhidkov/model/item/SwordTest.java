package ru.spbau.zhidkov.model.item;

import org.junit.Test;
import ru.spbau.zhidkov.model.characteristic.CharacteristicName;
import ru.spbau.zhidkov.model.characteristic.CharacteristicsSet;

import static org.junit.Assert.assertEquals;

public class SwordTest {

    @Test
    public void testOnlyAttackIncreases() {
        final Sword sword = new Sword(0, 10);
        final CharacteristicsSet characteristicsSet = new CharacteristicsSet(10, 20, 30);
        sword.onEquip(characteristicsSet);
        assertEquals(20, characteristicsSet.getValue(CharacteristicName.ATTACK));
        assertEquals(20, characteristicsSet.getValue(CharacteristicName.DEFENSE));
        assertEquals(30, characteristicsSet.getValue(CharacteristicName.HEALTH));
    }

    @Test
    public void testUnEquipOnlyAttackDecreases() {
        final Sword sword = new Sword(0, 10);
        final CharacteristicsSet characteristicsSet = new CharacteristicsSet(10, 20, 30);
        sword.unEquip(characteristicsSet);
        assertEquals(0, characteristicsSet.getValue(CharacteristicName.ATTACK));
        assertEquals(20, characteristicsSet.getValue(CharacteristicName.DEFENSE));
        assertEquals(30, characteristicsSet.getValue(CharacteristicName.HEALTH));
    }

}
