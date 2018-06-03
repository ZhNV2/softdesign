package ru.spbau.zhidkov.view.drawer;

import org.codetome.zircon.api.Position;
import org.codetome.zircon.api.terminal.Terminal;
import org.junit.Test;
import ru.spbau.zhidkov.model.characteristic.CharacteristicsSet;
import ru.spbau.zhidkov.model.gameplay.Game;
import ru.spbau.zhidkov.model.item.Shield;
import ru.spbau.zhidkov.model.item.Sword;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EquipmentDrawerTest extends AbstractDrawerTest {

    @Test
    public void testDraw() {
        final Game gameMock = mock(Game.class);
        final Terminal terminalMock = mock(Terminal.class);
        final EquipmentDrawer drawer = new EquipmentDrawer(gameMock, terminalMock);
        when(gameMock.listEquipment()).thenReturn(Arrays.asList(
                new Sword(1, 5),
                new Shield(2, 10)
        ));

        final TestDrawer expectedDrawer = new TestDrawer(5, 50);
        final TestDrawer realDrawer = new TestDrawer(5, 50);

        fill(expectedDrawer, Arrays.asList(
                "Equipped:",
                "",
                "sword      +5 /   /    a",
                "shield        /+10/    b"
        ));
        setUp(realDrawer, terminalMock);

        drawer.draw(new Position(0, 0));
        assertEquals(expectedDrawer, realDrawer);
    }
}
