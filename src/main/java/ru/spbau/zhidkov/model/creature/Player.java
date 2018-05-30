package ru.spbau.zhidkov.model.creature;

import org.codetome.zircon.api.Position;
import ru.spbau.zhidkov.model.characteristic.CharacteristicsSet;
import ru.spbau.zhidkov.model.item.Item;
import ru.spbau.zhidkov.model.item.Luggage;
import ru.spbau.zhidkov.model.terrain.Movement;
import ru.spbau.zhidkov.model.terrain.Terrain;
import ru.spbau.zhidkov.model.utils.GameConstants;

import java.util.List;
import java.util.Optional;

public class Player implements Creature {

    private final Luggage backpack = new Luggage(GameConstants.BACKPACK_LIMIT);
    private final Luggage equipment = new Luggage(GameConstants.EQUIPMENT_LIMIT);

    private Movement movement = Movement.NO_MOVE;
    private boolean isDead = false;

    private final CharacteristicsSet characteristicsSet = new CharacteristicsSet(GameConstants.INITIAL_PLAYER_ATTACK,
            GameConstants.INITIAL_PLAYER_DEFENSE, GameConstants.INITIAL_PLAYER_HEALTH);


    public void setMovement(Movement movement) {
        this.movement = movement;
    }

    @Override
    public Movement move(Position position, Terrain terrain) {
        return movement;
    }

    @Override
    public void onItem(Item item) {
        tryAddToBackpack(item);
    }

    public boolean tryAddToEquipment(Item item) {
        if (equipment.tryAdd(item)) {
            item.onEquip(characteristicsSet);
            return true;
        }
        return false;
    }

    public boolean tryAddToBackpack(Item item) {
        return backpack.tryAdd(item);
    }

    public boolean deleteFromEquipment(int id) {
        final Optional<Item> item = getFromEquipment(id);
        if (item.isPresent()) {
            item.get().unEquip(characteristicsSet);
            return equipment.delete(id);
        }
        return false;
    }

    public boolean deleteFromBackpack(int id) {
        return backpack.delete(id);
    }

    public Optional<Item> getFromEquipment(int id) {
        return equipment.get(id);
    }

    public Optional<Item> getFromBackpack(int id) {
        return backpack.get(id);
    }

    public List<Item> listEquipment() {
        return equipment.items();
    }

    public List<Item> listBackpack() {
        return backpack.items();
    }

    @Override
    public void onDepth() {
        isDead = true;
    }

    @Override
    public CharacteristicsSet getCharacteristics() {
        return characteristicsSet;
    }

    public boolean isDead() {
        return isDead;
    }
}
