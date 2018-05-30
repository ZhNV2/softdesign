package ru.spbau.zhidkov.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codetome.zircon.api.input.Input;
import org.codetome.zircon.api.input.InputType;
import org.codetome.zircon.api.input.KeyStroke;
import ru.spbau.zhidkov.model.gameplay.Game;
import ru.spbau.zhidkov.model.item.Item;
import ru.spbau.zhidkov.model.terrain.Movement;
import ru.spbau.zhidkov.view.drawer.GameDrawer;

import java.util.Optional;

public class Controller {

    private final static Logger LOG = LogManager.getLogger(Controller.class);

    private final Game game;
    private final GameDrawer gameDrawer;

    public Controller(Game game, GameDrawer gameDrawer) {
        this.game = game;
        this.gameDrawer = gameDrawer;
    }

    void process(Input input) {
        if (input.getInputType() == InputType.ArrowLeft) {
            LOG.debug("left arrow was pressed");
            game.movePlayer(Movement.LEFT);
        } else if (input.getInputType() == InputType.ArrowDown) {
            LOG.debug("down arrow was pressed");
            game.movePlayer(Movement.DOWN);
        } else if (input.getInputType() == InputType.ArrowRight) {
            LOG.debug("right arrow was pressed");
            game.movePlayer(Movement.RIGHT);
        } else if (input.getInputType() == InputType.ArrowUp) {
            LOG.debug("up arrow was pressed");
            game.movePlayer(Movement.UP);
        } else if (input.isKeyStroke()) {
            final KeyStroke keyStroke = input.asKeyStroke();
            final char c = keyStroke.getCharacter();
            LOG.debug(c + " key was pressed");
            final Integer id = gameDrawer.getIdByChar(c);
            LOG.debug("item " + id + " was managed");
            if (id != null) {
                final Optional<Item> optionalEquipmentItem = game.getFromEquipment(id);
                final Optional<Item> optionalBackpackItem = game.getFromBackpack(id);
                if (optionalEquipmentItem.isPresent()) {
                    final Item item = optionalEquipmentItem.get();
                    game.deleteFromEquipment(id);
                    game.tryAddToBackpack(item);
                } else if (optionalBackpackItem.isPresent()){
                    final Item item = optionalBackpackItem.get();
                    game.deleteFromBackpack(id);
                    if (!game.tryAddToEquipment(item)) {
                        game.tryAddToBackpack(item);
                    }
                }
            }
        }
    }
}
