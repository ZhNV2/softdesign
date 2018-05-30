package ru.spbau.zhidkov.model.creature;

import org.codetome.zircon.api.Position;
import ru.spbau.zhidkov.model.characteristic.CharacteristicsSet;
import ru.spbau.zhidkov.model.item.Item;
import ru.spbau.zhidkov.model.terrain.Movement;
import ru.spbau.zhidkov.model.terrain.Terrain;
import ru.spbau.zhidkov.model.terrain.TerrainUnit;

public interface Creature extends TerrainUnit {

    Movement move(Position position, Terrain terrain);

    void onItem(Item item);

    void onDepth();

    CharacteristicsSet getCharacteristics();

    @Override
    default Type getType() {
        return Type.CREATURE;
    }
}
