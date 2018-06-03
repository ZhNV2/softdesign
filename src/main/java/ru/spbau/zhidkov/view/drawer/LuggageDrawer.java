package ru.spbau.zhidkov.view.drawer;

import org.codetome.zircon.api.Position;
import org.codetome.zircon.api.terminal.Terminal;
import ru.spbau.zhidkov.model.characteristic.CharacteristicName;
import ru.spbau.zhidkov.model.gameplay.Game;
import ru.spbau.zhidkov.model.item.Item;

import java.util.List;

/**
 * Class for list items drawers common methods
 */
abstract public class LuggageDrawer extends AbstractDrawer implements Drawer {

    private static final int MAX_NAME_LEN = 10;
    private static final int MAX_CHARACTERISTIC_LEN = 12;

    public LuggageDrawer(Game game, Terminal terminal) {
        super(game, terminal);
    }

    public void draw(Position startPosition, List<Item> items, char startSymbol) {
        Position curPosition = new Position(startPosition.getColumn(), startPosition.getRow());
        for (int i = 0; i < items.size(); i++) {
            final Item item = items.get(i);
            final String update = fit(item.getUpdateDesc(CharacteristicName.ATTACK), 3) + "/" +
                    fit(item.getUpdateDesc(CharacteristicName.DEFENSE), 3) + "/" +
                    fit(item.getUpdateDesc(CharacteristicName.HEALTH), 3);
            final String output = fit(item.getName(), MAX_NAME_LEN) + " " + update + " " + (char)(startSymbol + i);
            drawString(curPosition, output);
            curPosition = curPosition.plus(new Position(0, 1));
        }
    }

    public void clean(Position startPosition, int cnt) {
        for (int i = 0; i < cnt; i++) {
            drawSpaces(startPosition.plus(new Position(0, i)), MAX_NAME_LEN + 1 + MAX_CHARACTERISTIC_LEN + 1 + 1);
        }
    }

    private String fit(String s, int len) {
        final StringBuilder sBuilder = new StringBuilder(s);
        while (sBuilder.length() < len) {
            sBuilder.append(' ');
        }
        return sBuilder.toString();
    }
}
