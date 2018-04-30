package ru.spbau.zhidkov.cli.command;

import org.junit.Test;
import ru.spbau.zhidkov.environment.Environment;
import ru.spbau.zhidkov.environment.EnvironmentImpl;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CommandsTest {

    @Test
    public void testAssignment() throws IOException {
        final Command assignment = new AssignmentCommand();
        final Environment<String, String> environment = new EnvironmentImpl<>();
        final CommandResult commandResult = assignment.execute(Collections.emptyList(), Arrays.asList("file", "tmp"), environment);
        assertEquals("tmp", environment.getOrDefault("file", ""));
        assertFalse(commandResult.isExit());
    }

    @Test
    public void testCatCommandFromFile() throws IOException {
        final Command cat = new CatCommand();
        final CommandResult commandResult = cat.execute(Collections.emptyList(),
                Collections.singletonList("src/test/resources/test_case_file.txt"), new EnvironmentImpl<>());
        assertEquals(Arrays.asList("ab cd", "e f", "   g"), commandResult.getOutput());
        assertFalse(commandResult.isExit());
    }

    @Test
    public void testCatCommandFromPipe() throws IOException {
        final Command cat = new CatCommand();
        final List<String> lines = Arrays.asList("  gfsdg sf g", " fg d");
        final CommandResult commandResult = cat.execute(lines, Collections.emptyList(), new EnvironmentImpl<>());
        assertEquals(lines, commandResult.getOutput());
        assertFalse(commandResult.isExit());
    }

    @Test
    public void testEchoFromArgs() throws IOException {
        final Command echo = new EchoCommand();
        final CommandResult commandResult = echo.execute(Collections.emptyList(),
                Arrays.asList("f s", " f"), new EnvironmentImpl<>());
        assertEquals(Collections.singletonList("f s  f"), commandResult.getOutput());
        assertFalse(commandResult.isExit());
    }

    @Test
    public void testEchoFromPipe() throws IOException {
        final Command echo = new CatCommand();
        final List<String> lines = Arrays.asList("  gfsdg sf g", " fg d");
        final CommandResult commandResult = echo.execute(lines, Collections.emptyList(), new EnvironmentImpl<>());
        assertEquals(lines, commandResult.getOutput());
        assertFalse(commandResult.isExit());
    }

    @Test
    public void testExit() throws IOException {
        final Command exit = new ExitCommand();
        final CommandResult commandResult = exit.execute(Collections.emptyList(), Collections.emptyList(), new EnvironmentImpl<>());
        assertTrue(commandResult.isExit());
    }

    @Test
    public void testWcFromPipe() throws IOException {
        final Command wc = new WcCommand();
        final CommandResult commandResult = wc.execute(Arrays.asList("ab cd", "e f", "   g"), Collections.emptyList(), new EnvironmentImpl<>());
        assertEquals(Collections.singletonList("3 5 12"), commandResult.getOutput());
        assertFalse(commandResult.isExit());
    }

    @Test
    public void testWcFromFile() throws IOException {
        final Command wc = new WcCommand();
        final CommandResult commandResult = wc.execute(Collections.emptyList(),
                Collections.singletonList("src/test/resources/test_case_file.txt"), new EnvironmentImpl<>());
        assertEquals(Collections.singletonList("3 5 12"), commandResult.getOutput());
        assertFalse(commandResult.isExit());
    }
}
