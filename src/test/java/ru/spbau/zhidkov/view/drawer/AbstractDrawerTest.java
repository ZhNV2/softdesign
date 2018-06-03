package ru.spbau.zhidkov.view.drawer;

import org.codetome.zircon.api.Position;
import org.codetome.zircon.api.terminal.Terminal;

import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyChar;
import static org.mockito.Mockito.when;

public class AbstractDrawerTest {

    public void fill(TestDrawer testDrawer, List<String> strings) {
        for (int i = 0; i < strings.size(); i++) {
            for (int j = 0; j < strings.get(i).length(); j++) {
                testDrawer.set(i, j, strings.get(i).charAt(j));
            }
        }
    }

    public void setUp(TestDrawer testDrawer, Terminal terminalMock) {
        when(terminalMock.setCharacterAt(any(), anyChar())).thenAnswer(invocation -> {
            final Position position = (Position) invocation.getArguments()[0];
            final char c = (char) invocation.getArguments()[1];
            testDrawer.set(position.getRow(), position.getColumn(), c);
            return null;
        });
    }
}
