package ru.spbau.zhidkov.view;

import org.codetome.zircon.api.Position;
import org.codetome.zircon.api.Size;
import org.codetome.zircon.api.builder.TerminalBuilder;
import org.codetome.zircon.api.terminal.Terminal;
import ru.spbau.zhidkov.model.gameplay.Game;
import ru.spbau.zhidkov.model.gameplay.Gameplay;
import ru.spbau.zhidkov.view.drawer.GameDrawer;


public class Main {

    public static void main(String[] args) throws InterruptedException {
        final Game game = new Gameplay();
        final Terminal terminal = TerminalBuilder.newBuilder()
                .initialTerminalSize(Size.of(60, 35))
                .build();
        final GameDrawer gameDrawer = new GameDrawer(game, terminal);
        final Controller controller = new Controller(game, gameDrawer);
        terminal.onInput(controller::process);
        while (!game.isPlayerDead()) {
            game.tick();
            gameDrawer.draw(new Position(0, 0));
            terminal.flush();
            Thread.sleep(300);
        }

    }

}
