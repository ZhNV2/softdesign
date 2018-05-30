package ru.spbau.zhidkov.model.gameplay;

import org.codetome.zircon.api.Position;
import ru.spbau.zhidkov.model.characteristic.CharacteristicName;
import ru.spbau.zhidkov.model.characteristic.CharacteristicsSetView;
import ru.spbau.zhidkov.model.characteristic.update.CharacteristicIncStrategy;
import ru.spbau.zhidkov.model.creature.Creature;
import ru.spbau.zhidkov.model.creature.MobFactory;
import ru.spbau.zhidkov.model.creature.Player;
import ru.spbau.zhidkov.model.item.Item;
import ru.spbau.zhidkov.model.item.ItemFactory;
import ru.spbau.zhidkov.model.terrain.*;
import ru.spbau.zhidkov.model.utils.GameConstants;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Gameplay implements Game {

    private final Player player = new Player();
    private final Terrain terrain = TerrainFactory.getINSTANCE().produceRandomTerrain(GameConstants.ROWS_CNT, GameConstants.COLUMNS_CNT);

    private int tick = 0;
    private Position playerPosition;

    public Gameplay() {
        playerPosition = terrain.addPlayerRandomly(player);
    }

    @Override
    public Position getPlayerPosition() {
        return playerPosition;
    }

    @Override
    public void movePlayer(Movement movement) {
        player.setMovement(movement);
    }

    @Override
    public TerrainView getTerrain() {
        return terrain;
    }

    @Override
    public CharacteristicsSetView getPlayersCharacteristics() {
        return player.getCharacteristics();
    }

    @Override
    public boolean tryAddToEquipment(Item item) {
        return player.tryAddToEquipment(item);
    }

    @Override
    public boolean tryAddToBackpack(Item item) {
        return player.tryAddToBackpack(item);
    }

    @Override
    public boolean deleteFromEquipment(int id) {
        return player.deleteFromEquipment(id);
    }

    @Override
    public boolean deleteFromBackpack(int id) {
        return player.deleteFromBackpack(id);
    }

    @Override
    public Optional<Item> getFromEquipment(int id) {
        return player.getFromEquipment(id);
    }

    @Override
    public Optional<Item> getFromBackpack(int id) {
        return player.getFromBackpack(id);
    }

    @Override
    public List<Item> listEquipment() {
        return player.listEquipment();
    }

    @Override
    public List<Item> listBackpack() {
        return player.listBackpack();
    }

    @Override
    public void tick() {
        tick++;
        shiftCreatures();
        player.setMovement(Movement.NO_MOVE);
        if (tick % GameConstants.HEALTH_UNIT_RESTORE_PERIOD == 0 &&
                player.getCharacteristics().getValue(CharacteristicName.HEALTH) < GameConstants.MAX_PLAYER_HEALTH) {
            player.getCharacteristics().update(CharacteristicName.HEALTH, new CharacteristicIncStrategy(1));
        }
        if (tick % GameConstants.NEW_ITEM_PERIOD == 0) {
            terrain.addItemRandomly(ItemFactory.getINSTANCE().produceOneCharacteristicBoosterRandomItem());
        }
        if (tick % GameConstants.NEW_MOB_PERIOD == 0) {
            terrain.addCreatureRandomly(MobFactory.getINSTANCE().produceRandomSkeleton());
        }
    }

    @Override
    public boolean isPlayerDead() {
        return player.isDead();
    }

    private void shiftCreatures() {
        final Set<TerrainUnit> used = new HashSet<>();
        for (int i = 0; i < GameConstants.ROWS_CNT; i++) {
            for (int j = 0; j < GameConstants.COLUMNS_CNT; j++) {
                final TerrainUnit terrainUnit = terrain.get(i, j);
                if (used.contains(terrainUnit)) {
                    continue;
                }
                used.add(terrainUnit);
                if (terrainUnit.getType() == TerrainUnit.Type.CREATURE) {
                    final Creature creature = (Creature) terrainUnit;
                    shiftCreature(i, j, creature);
                }
            }
        }
    }

    private void shiftCreature(int i, int j, Creature creature) {
        final Position oldPosition = new Position(j, i);
        final Movement move = creature.move(oldPosition, terrain);
        if (move == Movement.NO_MOVE) {
            return;
        }
        Position newPosition = move.shift(oldPosition);
        terrain.set(i, j, EmptyCell.getINSTANCE());

        final TerrainUnit neighbor = terrain.get(newPosition.getRow(), newPosition.getColumn());

        if (neighbor.getType() == TerrainUnit.Type.ITEM) {
            creature.onItem((Item) neighbor);
            terrain.set(newPosition.getRow(), newPosition.getColumn(), creature);
        } else if (neighbor.getType() == TerrainUnit.Type.CREATURE) {
            final Fight fight = new Fight(creature, (Creature) neighbor);
            terrain.set(newPosition.getRow(), newPosition.getColumn(), fight.fight());
        } else if (neighbor.getType() == TerrainUnit.Type.STONE) {
            newPosition = oldPosition;
            terrain.set(i, j, creature);
        } else if (neighbor.getType() == TerrainUnit.Type.EMPTY_CELL) {
            terrain.set(newPosition.getRow(), newPosition.getColumn(), creature);
        }
        if (oldPosition.equals(playerPosition)) {
            playerPosition = newPosition;
        }
    }
}
