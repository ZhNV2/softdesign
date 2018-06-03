package ru.spbau.zhidkov.model.item;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LuggageTest {

    @Test
    public void testAddTrue() {
        final Luggage luggage = new Luggage(10);
        assertTrue(luggage.tryAdd(mock(Item.class)));
    }

    @Test
    public void testAddInFullFalse() {
        final Luggage luggage = new Luggage(2);
        luggage.tryAdd(mock(Item.class));
        luggage.tryAdd(mock(Item.class));
        assertFalse(luggage.tryAdd(mock(Item.class)));
    }

    @Test
    public void testDeleteAfterAndIsTrue() {
        final Luggage luggage = new Luggage(2);
        final Item item = mock(Item.class);
        when(item.getId()).thenReturn(10);
        luggage.tryAdd(item);
        assertTrue(luggage.delete(10));
    }
}
