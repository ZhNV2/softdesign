package ru.spbau.zhidkov.view.drawer;

import org.codetome.zircon.api.Position;
import org.codetome.zircon.api.terminal.Terminal;
import ru.spbau.zhidkov.model.gameplay.Game;
import ru.spbau.zhidkov.model.terrain.TerrainUnit;
import ru.spbau.zhidkov.model.terrain.TerrainView;

public class MapDrawer extends AbstractDrawer implements Drawer {

    public MapDrawer(Game game, Terminal terminal) {
        super(game, terminal);
    }

    @Override
    public void draw(Position startPosition) {
        final TerrainView terrain = game.getTerrain();
        final Position playerPosition = game.getPlayerPosition();
        for (int i = 0; i < terrain.getRowsCnt(); i++) {
            for (int j = 0; j< terrain.getColumnsCnt(); j++) {
                final TerrainUnit unit = terrain.get(i, j);
                char c = ' ';
                if (playerPosition.getColumn() == j && playerPosition.getRow() == i) {
                    c = 'P';
                } else if (unit.getType() == TerrainUnit.Type.EMPTY_CELL) {
                    c = '.';
                } else if (unit.getType() == TerrainUnit.Type.STONE) {
                    c = 'X';
                } else if (unit.getType() == TerrainUnit.Type.CREATURE) {
                    c = 'M';
                } else if (unit.getType() == TerrainUnit.Type.ITEM) {
                    c = 'I';
                }
                terminal.setCharacterAt(new Position(j, i), c);
            }
        }
    }
}
