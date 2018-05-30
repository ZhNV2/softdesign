package ru.spbau.zhidkov.view.drawer;

import org.codetome.zircon.api.Position;
import org.codetome.zircon.api.terminal.Terminal;
import ru.spbau.zhidkov.model.gameplay.Game;
import ru.spbau.zhidkov.model.item.Item;
import ru.spbau.zhidkov.model.utils.GameConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameDrawer implements Drawer {

    private final Map<Character, Integer> keyToId = new HashMap<>();
    private final Game game;
    private final MapDrawer mapDrawer;
    private final PlayerCharacteristicsDrawer playerCharacteristicsDrawer;
    private final EquipmentDrawer equipmentDrawer;
    private final BackpackDrawer backpackDrawer;

    public GameDrawer(Game game, Terminal terminal) {
        this.game = game;
        mapDrawer = new MapDrawer(game, terminal);
        playerCharacteristicsDrawer = new PlayerCharacteristicsDrawer(game, terminal);
        equipmentDrawer = new EquipmentDrawer(game, terminal);
        backpackDrawer = new BackpackDrawer(game, terminal);
    }

    public Integer getIdByChar(char c) {
        return keyToId.get(c);
    }

    public void draw(Position startPosition) {
        keyToId.clear();
        fillKeyToIdForLuggage(game.listEquipment(), 'a');
        fillKeyToIdForLuggage(game.listBackpack(), (char)('a'  + GameConstants.EQUIPMENT_LIMIT));
        mapDrawer.draw(new Position(0, 0));
        playerCharacteristicsDrawer.draw(new Position(GameConstants.COLUMNS_CNT + 1, 0));
        equipmentDrawer.draw(new Position(GameConstants.COLUMNS_CNT + 1, 2));
        backpackDrawer.draw(new Position(GameConstants.COLUMNS_CNT + 1, 5 + GameConstants.EQUIPMENT_LIMIT));
    }

    private void fillKeyToIdForLuggage(List<Item> items, char firstSymbol) {
        for (int i = 0; i < items.size(); i++) {
            keyToId.put((char)(firstSymbol + i), items.get(i).getId());
        }
    }



}
