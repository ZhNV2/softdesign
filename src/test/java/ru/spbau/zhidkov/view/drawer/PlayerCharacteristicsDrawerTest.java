package ru.spbau.zhidkov.view.drawer;

import org.codetome.zircon.api.Position;
import org.codetome.zircon.api.terminal.Terminal;
import org.junit.Test;
import ru.spbau.zhidkov.model.characteristic.CharacteristicsSet;
import ru.spbau.zhidkov.model.gameplay.Game;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerCharacteristicsDrawerTest extends AbstractDrawerTest {

    @Test
    public void testDraw() {
        final Game gameMock = mock(Game.class);
        final Terminal terminalMock = mock(Terminal.class);
        final PlayerCharacteristicsDrawer drawer = new PlayerCharacteristicsDrawer(gameMock, terminalMock);
        when(gameMock.getPlayersCharacteristics()).thenReturn(new CharacteristicsSet(2, 6, 10));

        final TestDrawer expectedDrawer = new TestDrawer(1, 50);
        final TestDrawer realDrawer = new TestDrawer(1, 50);

        fill(expectedDrawer, Collections.singletonList("A/D/H = 2/6/10"));
        setUp(realDrawer, terminalMock);

        drawer.draw(new Position(0, 0));
        assertEquals(expectedDrawer, realDrawer);


    }
}
