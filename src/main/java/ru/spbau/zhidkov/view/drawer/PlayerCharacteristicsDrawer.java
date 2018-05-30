package ru.spbau.zhidkov.view.drawer;

import org.codetome.zircon.api.Position;
import org.codetome.zircon.api.terminal.Terminal;
import ru.spbau.zhidkov.model.characteristic.CharacteristicName;
import ru.spbau.zhidkov.model.characteristic.CharacteristicsSetView;
import ru.spbau.zhidkov.model.gameplay.Game;

public class PlayerCharacteristicsDrawer extends AbstractDrawer implements Drawer {

    private final static int MAX_LEN = 25;

    public PlayerCharacteristicsDrawer(Game game, Terminal terminal) {
        super(game, terminal);
    }

    @Override
    public void draw(Position startPosition) {
        final CharacteristicsSetView characteristics = game.getPlayersCharacteristics();
        final String output = "A/D/H = "
                + characteristics.getValue(CharacteristicName.ATTACK) + "/"
                + characteristics.getValue(CharacteristicName.DEFENSE) + "/"
                + characteristics.getValue(CharacteristicName.HEALTH);
        drawSpaces(startPosition, MAX_LEN);
        drawString(startPosition, output);
    }


}
