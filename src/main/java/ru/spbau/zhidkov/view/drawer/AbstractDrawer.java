package ru.spbau.zhidkov.view.drawer;

import org.codetome.zircon.api.Position;
import org.codetome.zircon.api.terminal.Terminal;
import ru.spbau.zhidkov.model.gameplay.Game;

/**
 * Class for common drawers' methods
 */
abstract public class AbstractDrawer {

    protected final Game game;
    protected final Terminal terminal;

    public AbstractDrawer(Game game, Terminal terminal) {
        this.game = game;
        this.terminal = terminal;
    }

    protected void drawString(Position startPosition, String s) {
        for (int i = 0; i < s.length(); i++) {
            terminal.setCharacterAt(startPosition.plus(new Position(i, 0)), s.charAt(i));
        }
    }

    protected void drawSpaces(Position startPosition, int len) {
        for (int i = 0; i < len; i++) {
            terminal.setCharacterAt(startPosition.plus(new Position(i, 0)), ' ');
        }
    }


}
