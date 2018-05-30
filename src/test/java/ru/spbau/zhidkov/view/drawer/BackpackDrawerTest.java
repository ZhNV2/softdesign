package ru.spbau.zhidkov.view.drawer;

import org.codetome.zircon.api.Position;
import org.codetome.zircon.api.terminal.Terminal;
import org.junit.Test;
import ru.spbau.zhidkov.model.gameplay.Game;
import ru.spbau.zhidkov.model.item.Shield;
import ru.spbau.zhidkov.model.item.Sword;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BackpackDrawerTest extends AbstractDrawerTest {

    @Test
    public void testDraw() {
        final Game gameMock = mock(Game.class);
        final Terminal terminalMock = mock(Terminal.class);
        final BackpackDrawer drawer = new BackpackDrawer(gameMock, terminalMock);
        when(gameMock.listBackpack()).thenReturn(Arrays.asList(
                new Sword(1, 5),
                new Shield(2, 10)
        ));

        final TestDrawer expectedDrawer = new TestDrawer(12, 50);
        final TestDrawer realDrawer = new TestDrawer(12, 50);


        fill(expectedDrawer, Arrays.asList(
                "Backpack:",
                "",
                "sword      +5 /   /    d",
                "shield        /+10/    e"
        ));
        setUp(realDrawer, terminalMock);

        drawer.draw(new Position(0, 0));
        assertEquals(expectedDrawer, realDrawer);
    }
}
