package ru.spbau.zhidkov.model.gameplay;

import org.junit.Test;
import ru.spbau.zhidkov.model.characteristic.CharacteristicName;
import ru.spbau.zhidkov.model.characteristic.CharacteristicsSet;
import ru.spbau.zhidkov.model.creature.Creature;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FightTest {

    @Test
    public void testFight() {
        final CharacteristicsSet characteristicsSet1 = new CharacteristicsSet(10, 5, 10);
        final CharacteristicsSet characteristicsSet2 = new CharacteristicsSet(6, 5, 30);
        final Creature creature1 = mock(Creature.class);
        when(creature1.getCharacteristics()).thenReturn(characteristicsSet1);
        final Creature creature2 = mock(Creature.class);
        when(creature2.getCharacteristics()).thenReturn(characteristicsSet2);
        assertEquals(creature1, new Fight(creature1, creature2).fight());
        verify(creature2).onDepth();
        assertEquals(5, creature1.getCharacteristics().getValue(CharacteristicName.HEALTH));
    }
}
