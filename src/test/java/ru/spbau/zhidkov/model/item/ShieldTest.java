package ru.spbau.zhidkov.model.item;

import org.junit.Test;
import ru.spbau.zhidkov.model.characteristic.CharacteristicName;
import ru.spbau.zhidkov.model.characteristic.CharacteristicsSet;

import static org.junit.Assert.assertEquals;

public class ShieldTest {

    @Test
    public void testOnlyDefenseIncreases() {
        final Shield shield = new Shield(0, 15);
        final CharacteristicsSet characteristicsSet = new CharacteristicsSet(10, 20, 30);
        shield.onEquip(characteristicsSet);
        assertEquals(10, characteristicsSet.getValue(CharacteristicName.ATTACK));
        assertEquals(35, characteristicsSet.getValue(CharacteristicName.DEFENSE));
        assertEquals(30, characteristicsSet.getValue(CharacteristicName.HEALTH));
    }

    @Test
    public void testUnEquipOnlyDefenseDecreases() {
        final Shield shield = new Shield(0, 15);
        final CharacteristicsSet characteristicsSet = new CharacteristicsSet(10, 20, 30);
        shield.unEquip(characteristicsSet);
        assertEquals(10, characteristicsSet.getValue(CharacteristicName.ATTACK));
        assertEquals(5, characteristicsSet.getValue(CharacteristicName.DEFENSE));
        assertEquals(30, characteristicsSet.getValue(CharacteristicName.HEALTH));
    }
}
