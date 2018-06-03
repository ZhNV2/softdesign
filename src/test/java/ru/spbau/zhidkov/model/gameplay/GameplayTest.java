package ru.spbau.zhidkov.model.gameplay;

import org.codetome.zircon.api.Position;
import org.junit.Test;
import ru.spbau.zhidkov.model.characteristic.CharacteristicsSet;
import ru.spbau.zhidkov.model.creature.Creature;
import ru.spbau.zhidkov.model.creature.Player;
import ru.spbau.zhidkov.model.item.Item;
import ru.spbau.zhidkov.model.item.ItemFactory;
import ru.spbau.zhidkov.model.terrain.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GameplayTest {

    @Test
    public void testCreatureMovesAfterTick() {
        final Player player = mock(Player.class);
        final Terrain terrain = TerrainFactory.getINSTANCE().produceRandomTerrain(10, 10, 0, 0);
        final Game game = new Gameplay(player, terrain);
        final Creature creature = mock(Creature.class);
        when(creature.move(any(), any())).thenReturn(Movement.RIGHT);
        when(creature.getType()).thenReturn(TerrainUnit.Type.CREATURE);
        terrain.tryAdd(1, 1, creature);
        game.tick();
        assertEquals(TerrainUnit.Type.EMPTY_CELL, terrain.get(1, 1).getType());
        assertEquals(creature, terrain.get(1, 2));
    }

    @Test
    public void testCreaturesFightOneLeft() {
        final Player player = mock(Player.class);
        final Terrain terrain = TerrainFactory.getINSTANCE().produceRandomTerrain(10, 10, 0, 0);
        final Game game = new Gameplay(player, terrain);
        final Creature creatureWinner = mock(Creature.class);
        when(creatureWinner.move(any(), any())).thenReturn(Movement.LEFT);
        when(creatureWinner.getType()).thenReturn(TerrainUnit.Type.CREATURE);
        when(creatureWinner.getCharacteristics()).thenReturn(new CharacteristicsSet(100, 100, 100));
        final Creature creatureLoser = mock(Creature.class);
        when(creatureLoser.move(any(), any())).thenReturn(Movement.RIGHT);
        when(creatureLoser.getType()).thenReturn(TerrainUnit.Type.CREATURE);
        when(creatureLoser.getCharacteristics()).thenReturn(new CharacteristicsSet(1, 1, 1));
        terrain.tryAdd(1, 1, creatureLoser);
        terrain.tryAdd(1, 3, creatureWinner);
        game.tick();
        assertEquals(TerrainUnit.Type.EMPTY_CELL, terrain.get(1, 1).getType());
        assertEquals(TerrainUnit.Type.EMPTY_CELL, terrain.get(1, 3).getType());
        assertEquals(creatureWinner, terrain.get(1, 2));
    }

    @Test
    public void testPlayerTakesItem() {
        final Player player = mock(Player.class);
        final CharacteristicsSet characteristicsSetMock = mock(CharacteristicsSet.class);

        when(player.getCharacteristics()).thenReturn(characteristicsSetMock);
        when(player.getType()).thenReturn(TerrainUnit.Type.CREATURE);
        final Terrain terrain = TerrainFactory.getINSTANCE().produceRandomTerrain(10, 10, 0, 0);
        final Game game = new Gameplay(player, terrain);
        final Position playerPosition = game.getPlayerPosition();
        final Item item = ItemFactory.getINSTANCE().produceOneCharacteristicBoosterRandomItem();

        if (playerPosition.getColumn() != 1) {
            when(player.move(any(), any())).thenReturn(Movement.LEFT);
            terrain.tryAdd(playerPosition.getRow(), playerPosition.getColumn() - 1, item);
        } else {
            when(player.move(any(), any())).thenReturn(Movement.RIGHT);
            terrain.tryAdd(playerPosition.getRow(), playerPosition.getColumn() + 1, item);
        }
        game.tick();
        verify(player).onItem(eq(item));
        assertEquals(player, terrain.get(game.getPlayerPosition().getRow(), game.getPlayerPosition().getColumn()));
    }

    @Test
    public void testCreatureStopsOnStone() {
        final Player player = mock(Player.class);
        final Terrain terrain = TerrainFactory.getINSTANCE().produceRandomTerrain(10, 10, 0, 0);
        final Game game = new Gameplay(player, terrain);
        final Creature creature = mock(Creature.class);
        when(creature.move(any(), any())).thenReturn(Movement.LEFT);
        when(creature.getType()).thenReturn(TerrainUnit.Type.CREATURE);
        terrain.tryAdd(1, 1, creature);
        game.tick();
        assertEquals(Stone.getINSTANCE(), terrain.get(1, 0));
        assertEquals(creature, terrain.get(1, 1));

    }
}
