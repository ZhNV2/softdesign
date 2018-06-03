package ru.spbau.zhidkov.view;

import org.codetome.zircon.api.input.Input;
import org.codetome.zircon.api.input.InputType;
import org.codetome.zircon.api.input.KeyStroke;
import org.junit.Test;
import ru.spbau.zhidkov.model.gameplay.Game;
import ru.spbau.zhidkov.model.item.Item;
import ru.spbau.zhidkov.view.drawer.GameDrawer;

import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class ControllerTest {

    @Test
    public void testClickOnBackpackNonFullEquipment() {
        final GameDrawer gameDrawerMock = mock(GameDrawer.class);
        final Game gameMock = mock(Game.class);
        final Controller controller = new Controller(gameMock, gameDrawerMock);
        final char c = 'a';
        final int id = 1;
        final Item itemMock = mock(Item.class);

        when(itemMock.getId()).thenReturn(id);
        when(gameDrawerMock.getIdByChar(eq(c))).thenReturn(id);
        when(gameMock.tryAddToEquipment(any())).thenReturn(true);
        when(gameMock.getFromBackpack(eq(id))).thenReturn(Optional.of(itemMock));
        when(gameMock.getFromEquipment(eq(id))).thenReturn(Optional.empty());

        controller.process(buildInput(c));

        verify(gameMock).deleteFromBackpack(eq(id));
        verify(gameMock).tryAddToEquipment(eq(itemMock));
        verify(gameMock, times(0)).tryAddToBackpack(eq(itemMock));

    }

    @Test
    public void testClickOnBackpackFullEquipment() {
        final GameDrawer gameDrawerMock = mock(GameDrawer.class);
        final Game gameMock = mock(Game.class);
        final Controller controller = new Controller(gameMock, gameDrawerMock);
        final char c = 'a';
        final int id = 1;
        final Item itemMock = mock(Item.class);

        when(itemMock.getId()).thenReturn(id);
        when(gameDrawerMock.getIdByChar(eq(c))).thenReturn(id);
        when(gameMock.tryAddToEquipment(any())).thenReturn(false);
        when(gameMock.getFromBackpack(eq(id))).thenReturn(Optional.of(itemMock));
        when(gameMock.getFromEquipment(eq(id))).thenReturn(Optional.empty());

        controller.process(buildInput(c));

        verify(gameMock).deleteFromBackpack(eq(id));
        verify(gameMock).tryAddToEquipment(eq(itemMock));
        verify(gameMock).tryAddToBackpack(eq(itemMock));

    }

    @Test
    public void testClickOnEquipmentNotFullBackpack() {
        final GameDrawer gameDrawerMock = mock(GameDrawer.class);
        final Game gameMock = mock(Game.class);
        final Controller controller = new Controller(gameMock, gameDrawerMock);
        final char c = 'a';
        final int id = 1;
        final Item itemMock = mock(Item.class);

        when(itemMock.getId()).thenReturn(id);
        when(gameDrawerMock.getIdByChar(eq(c))).thenReturn(id);
        when(gameMock.tryAddToBackpack(any())).thenReturn(true);
        when(gameMock.getFromBackpack(eq(id))).thenReturn(Optional.empty());
        when(gameMock.getFromEquipment(eq(id))).thenReturn(Optional.of(itemMock));

        controller.process(buildInput(c));

        verify(gameMock).deleteFromEquipment(eq(id));
        verify(gameMock).tryAddToBackpack(eq(itemMock));
        verify(gameMock, times(0)).tryAddToEquipment(eq(itemMock));

    }

    @Test
    public void testClickOnEquipmentFullBackpack() {
        final GameDrawer gameDrawerMock = mock(GameDrawer.class);
        final Game gameMock = mock(Game.class);
        final Controller controller = new Controller(gameMock, gameDrawerMock);
        final char c = 'a';
        final int id = 1;
        final Item itemMock = mock(Item.class);

        when(itemMock.getId()).thenReturn(id);
        when(gameDrawerMock.getIdByChar(eq(c))).thenReturn(id);
        when(gameMock.tryAddToBackpack(any())).thenReturn(false);
        when(gameMock.getFromBackpack(eq(id))).thenReturn(Optional.empty());
        when(gameMock.getFromEquipment(eq(id))).thenReturn(Optional.of(itemMock));

        controller.process(buildInput(c));

        verify(gameMock).deleteFromEquipment(eq(id));
        verify(gameMock).tryAddToBackpack(eq(itemMock));
        verify(gameMock, times(0)).tryAddToEquipment(eq(itemMock));

    }

    private Input buildInput(char c) {
        return new KeyStroke(c, InputType.Character, false, false, false);
    }
}
