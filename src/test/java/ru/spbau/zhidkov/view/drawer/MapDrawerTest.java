package ru.spbau.zhidkov.view.drawer;

import org.codetome.zircon.api.Position;
import org.codetome.zircon.api.terminal.Terminal;
import org.junit.Test;
import ru.spbau.zhidkov.model.creature.MobFactory;
import ru.spbau.zhidkov.model.creature.Player;
import ru.spbau.zhidkov.model.gameplay.Game;
import ru.spbau.zhidkov.model.item.Sword;
import ru.spbau.zhidkov.model.terrain.EmptyCell;
import ru.spbau.zhidkov.model.terrain.Stone;
import ru.spbau.zhidkov.model.terrain.TerrainView;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MapDrawerTest extends AbstractDrawerTest {

    @Test
    public void testDraw() {
        final Game gameMock = mock(Game.class);
        final Terminal terminalMock = mock(Terminal.class);
        final TerrainView terrainView = mock(TerrainView.class);
        when(terrainView.getRowsCnt()).thenReturn(2);
        when(terrainView.getColumnsCnt()).thenReturn(3);
        when(terrainView.get(0, 0)).thenReturn(Stone.getINSTANCE());
        when(terrainView.get(1, 0)).thenReturn(EmptyCell.getINSTANCE());
        when(terrainView.get(0, 1)).thenReturn(MobFactory.getINSTANCE().produceRandomSkeleton());
        when(terrainView.get(1, 1)).thenReturn(new Sword(0, 0));
        when(terrainView.get(0, 2)).thenReturn(new Player());
        when(terrainView.get(1, 2)).thenReturn(Stone.getINSTANCE());
        when(gameMock.getTerrain()).thenReturn(terrainView);
        when(gameMock.getPlayerPosition()).thenReturn(new Position(2, 0));
        final MapDrawer mapDrawer = new MapDrawer(gameMock, terminalMock);

        final TestDrawer expectedDrawer = new TestDrawer(5, 5);
        final TestDrawer realDrawer = new TestDrawer(5, 5);

        fill(expectedDrawer, Arrays.asList(
                "XMP",
                ".IX"
        ));
        setUp(realDrawer, terminalMock);
        mapDrawer.draw(new Position(0, 0));


        assertEquals(expectedDrawer, realDrawer);

    }
}
