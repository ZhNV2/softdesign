package ru.spbau.zhidkov.model.gameplay;

import org.codetome.zircon.api.Position;
import ru.spbau.zhidkov.model.characteristic.CharacteristicsSetView;
import ru.spbau.zhidkov.model.item.Item;
import ru.spbau.zhidkov.model.terrain.Movement;
import ru.spbau.zhidkov.model.terrain.TerrainView;

import java.util.List;
import java.util.Optional;

/**
 * Main interface for interaction with model component
 */
public interface Game {

    Position getPlayerPosition();

    void movePlayer(Movement movement);

    TerrainView getTerrain();

    CharacteristicsSetView getPlayersCharacteristics();

    boolean tryAddToEquipment(Item item);

    boolean tryAddToBackpack(Item item);

    boolean deleteFromEquipment(int id);

    boolean deleteFromBackpack(int id);

    Optional<Item> getFromEquipment(int id);

    Optional<Item> getFromBackpack(int id);

    List<Item> listEquipment();

    List<Item> listBackpack();

    void tick();

    boolean isPlayerDead();

}
