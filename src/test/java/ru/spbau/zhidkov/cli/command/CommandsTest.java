package ru.spbau.zhidkov.cli.command;

import org.junit.Test;
import ru.spbau.zhidkov.cli.commandArgsParsers.CommandArgParsingException;
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
    public void testAssignment() throws IOException, CommandArgParsingException {
        final Command assignment = new AssignmentCommand();
        final Environment<String, String> environment = new EnvironmentImpl<>();
        final CommandResult commandResult = assignment.execute(Collections.emptyList(), Arrays.asList("file", "tmp"), environment);
        assertEquals("tmp", environment.getOrDefault("file", ""));
        assertFalse(commandResult.isExit());
    }

    @Test
    public void testCatCommandFromFile() throws IOException, CommandArgParsingException {
        final Command cat = new CatCommand();
        final CommandResult commandResult = cat.execute(Collections.emptyList(),
                Collections.singletonList("src/test/resources/test_case_file.txt"), new EnvironmentImpl<>());
        assertEquals(Arrays.asList("ab cd", "e f", "   g"), commandResult.getOutput());
        assertFalse(commandResult.isExit());
    }

    @Test
    public void testCatCommandFromPipe() throws IOException, CommandArgParsingException {
        final Command cat = new CatCommand();
        final List<String> lines = Arrays.asList("  gfsdg sf g", " fg d");
        final CommandResult commandResult = cat.execute(lines, Collections.emptyList(), new EnvironmentImpl<>());
        assertEquals(lines, commandResult.getOutput());
        assertFalse(commandResult.isExit());
    }

    @Test
    public void testEchoFromArgs() throws IOException, CommandArgParsingException {
        final Command echo = new EchoCommand();
        final CommandResult commandResult = echo.execute(Collections.emptyList(),
                Arrays.asList("f s", " f"), new EnvironmentImpl<>());
        assertEquals(Collections.singletonList("f s  f"), commandResult.getOutput());
        assertFalse(commandResult.isExit());
    }

    @Test
    public void testEchoFromPipe() throws IOException, CommandArgParsingException {
        final Command echo = new CatCommand();
        final List<String> lines = Arrays.asList("  gfsdg sf g", " fg d");
        final CommandResult commandResult = echo.execute(lines, Collections.emptyList(), new EnvironmentImpl<>());
        assertEquals(lines, commandResult.getOutput());
        assertFalse(commandResult.isExit());
    }

    @Test
    public void testExit() throws IOException, CommandArgParsingException {
        final Command exit = new ExitCommand();
        final CommandResult commandResult = exit.execute(Collections.emptyList(), Collections.emptyList(), new EnvironmentImpl<>());
        assertTrue(commandResult.isExit());
    }

    @Test
    public void testWcFromPipe() throws IOException, CommandArgParsingException {
        final Command wc = new WcCommand();
        final CommandResult commandResult = wc.execute(Arrays.asList("ab cd", "e f", "   g"), Collections.emptyList(), new EnvironmentImpl<>());
        assertEquals(Collections.singletonList("3 5 12"), commandResult.getOutput());
        assertFalse(commandResult.isExit());
    }

    @Test
    public void testWcFromFile() throws IOException, CommandArgParsingException {
        final Command wc = new WcCommand();
        final CommandResult commandResult = wc.execute(Collections.emptyList(),
                Collections.singletonList("src/test/resources/test_case_file.txt"), new EnvironmentImpl<>());
        assertEquals(Collections.singletonList("3 5 12"), commandResult.getOutput());
        assertFalse(commandResult.isExit());
    }

    @Test
    public void testGrepMatchLetter() throws IOException, CommandArgParsingException {
        final Command grep = new GrepCommand();
        final List<String> input = Collections.singletonList("abc");
        final CommandResult commandResult = grep.execute(input, Collections.singletonList("a"), new EnvironmentImpl<>());
        assertEquals(input, commandResult.getOutput());
    }

    @Test
    public void testGrepMatchesRegex() throws IOException, CommandArgParsingException {
        final Command grep = new GrepCommand();
        final List<String> input = Collections.singletonList("abc");
        final CommandResult commandResult = grep.execute(input, Collections.singletonList("a[a-z]"), new EnvironmentImpl<>());
        assertEquals(input, commandResult.getOutput());
    }

    @Test
    public void testGrepDoesNotMatchRegex() throws IOException, CommandArgParsingException {
        final Command grep = new GrepCommand();
        final List<String> input = Collections.singletonList("abc");
        final CommandResult commandResult = grep.execute(input, Collections.singletonList("a[c-z]"), new EnvironmentImpl<>());
        assertEquals(Collections.emptyList(), commandResult.getOutput());
    }

    @Test
    public void testGrepIgnoreCase() throws IOException, CommandArgParsingException {
        final Command grep = new GrepCommand();
        final List<String> input = Collections.singletonList("ABc");
        final CommandResult commandResult = grep.execute(input, Arrays.asList("-i", "a[b-z]"), new EnvironmentImpl<>());
        assertEquals(input, commandResult.getOutput());
    }

    @Test
    public void testGrepDoesNotMatchWordRegex() throws IOException, CommandArgParsingException {
        final Command grep = new GrepCommand();
        final List<String> input = Collections.singletonList("bcd");
        final CommandResult commandResult = grep.execute(input, Arrays.asList("-w", "bc"), new EnvironmentImpl<>());
        assertEquals(Collections.emptyList(), commandResult.getOutput());
    }

    @Test
    public void testGrepMatchesWordRegex() throws IOException, CommandArgParsingException {
        final Command grep = new GrepCommand();
        final List<String> input = Collections.singletonList("a bc");
        final CommandResult commandResult = grep.execute(input, Arrays.asList("-w", "bc"), new EnvironmentImpl<>());
        assertEquals(input, commandResult.getOutput());
    }

    @Test
    public void testGrepAfterContext() throws IOException, CommandArgParsingException {
        final Command grep = new GrepCommand();
        final List<String> input = Arrays.asList("ab", "ac", "ad");
        final CommandResult commandResult = grep.execute(input, Arrays.asList("-A", "1", "ab"), new EnvironmentImpl<>());
        assertEquals(input.subList(0, 2), commandResult.getOutput());
    }

    @Test
    public void testGrepFromFile() throws IOException, CommandArgParsingException {
        final Command grep = new GrepCommand();
        final CommandResult commandResult = grep.execute(Collections.emptyList(),
                Arrays.asList("-i", "-A", "1", "e\\s[A-Z]", "src/test/resources/test_case_file.txt"),
                new EnvironmentImpl<>());
        assertEquals(Arrays.asList("e f", "   g"), commandResult.getOutput());
    }

}