package ru.spbau.zhidkov.parser;

import org.junit.Test;
import ru.spbau.zhidkov.environment.Environment;
import ru.spbau.zhidkov.environment.EnvironmentImpl;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;

public class ParserTest {

    @Test
    public void testCat() {
        testParser("cat", new EnvironmentImpl<>(), singletonList(new CommandCall("cat", emptyList())));
    }

    @Test
    public void testCatWithArguments() {
        testParser("cat a b c", new EnvironmentImpl<>(), singletonList(
                new CommandCall("cat", Arrays.asList("a", "b", "c"))));
    }

    @Test
    public void testSimpleAssignment() {
        testParser("a=b", new EnvironmentImpl<>(), singletonList(new CommandCall("=", Arrays.asList("a", "b"))));
    }


    @Test
    public void testSimpleSubstitution() {
        final Environment<String, String> environment = new EnvironmentImpl<>();
        environment.set("x", "a");
        testParser("$x", environment, singletonList(new CommandCall("a", emptyList())));
    }

    @Test
    public void testSimplePipe() {
        testParser("cat | wc", new EnvironmentImpl<>(), Arrays.asList(
                new CommandCall("cat", emptyList()),
                new CommandCall("wc", emptyList())
        ));
    }

    @Test
    public void testPipeWithoutSpaces() {
        testParser("cat a|wc b", new EnvironmentImpl<>(), Arrays.asList(
                new CommandCall("cat", singletonList("a")),
                new CommandCall("wc", singletonList("b"))
        ));
    }

    @Test
    public void testAssignmentWithSpaces() {
        testParser("papa roach=e rock", new EnvironmentImpl<>(), singletonList(new CommandCall("=",
                Arrays.asList("papa roach", "e rock"))));
    }

    @Test
    public void testAssignmentWithPipe() {
        testParser("a=b|c", new EnvironmentImpl<>(), Arrays.asList(
                new CommandCall("=", Arrays.asList("a", "b")),
                new CommandCall("c", emptyList())
        ));
    }

    @Test
    public void testSeveralAssignment() {
        testParser("a=b|c=d", new EnvironmentImpl<>(), Arrays.asList(
                new CommandCall("=", Arrays.asList("a", "b")),
                new CommandCall("=", Arrays.asList("c", "d"))
        ));
    }

    @Test
    public void testSubstitutionInsideSingleQuotes() {
        final Environment<String, String> environment = new EnvironmentImpl<>();
        environment.set("tmp", "x");
        testParser("cat \'$tmp\'", environment, singletonList(new CommandCall("cat", singletonList("$tmp"))));
    }

    @Test
    public void testSubstitutionInsideDoubleQuotes() {
        final Environment<String, String> environment = new EnvironmentImpl<>();
        environment.set("tmp", "x");
        testParser("cat \"$tmp\"", environment, singletonList(new CommandCall("cat", singletonList("x"))));
    }

    @Test
    public void testDoubleSubstitution() {
        final Environment<String, String> environment = new EnvironmentImpl<>();
        environment.set("cmd", "cat");
        environment.set("arg", "file");
        testParser("$cmd $arg", environment, singletonList(new CommandCall("cat", singletonList("file"))));
    }

    @Test(expected = ParseException.class)
    public void testNoTextBetweenPipes() {
        testParser("cat || wc", new EnvironmentImpl<>(), emptyList());
    }

    @Test(expected = ParseException.class)
    public void testUnmatchedQuotes() {
        testParser("tmp\'f", new EnvironmentImpl<>(), emptyList());
    }

    @Test(expected = ParseException.class)
    public void testVariableNotInScope() {
        testParser("$tmp", new EnvironmentImpl<>(), emptyList());
    }


    private void testParser(String text, Environment<String, String> environment, List<CommandCall> awaitedResult) {
        final List<CommandCall> actualResult = new Parser().process(environment, text);
        assertEquals(actualResult, awaitedResult);
    }

}
