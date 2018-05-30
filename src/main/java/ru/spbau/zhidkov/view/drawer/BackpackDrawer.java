package ru.spbau.zhidkov.view.drawer;

import org.codetome.zircon.api.Position;
import org.codetome.zircon.api.terminal.Terminal;
import ru.spbau.zhidkov.model.gameplay.Game;
import ru.spbau.zhidkov.model.utils.GameConstants;

public class BackpackDrawer extends LuggageDrawer {

    public BackpackDrawer(Game game, Terminal terminal) {
        super(game, terminal);
    }

    @Override
    public void draw(Position startPosition) {
        Position curPosition = new Position(startPosition.getColumn(), startPosition.getRow());
        drawString(curPosition, "Backpack:");
        curPosition = curPosition.plus(new Position(0, 2));
        clean(curPosition, GameConstants.BACKPACK_LIMIT);
        draw(curPosition, game.listBackpack(), (char)('a' + GameConstants.EQUIPMENT_LIMIT));
    }
}
