package ru.spbau.zhidkov.cli.commandArgsParser.grep;

import org.junit.Test;
import ru.spbau.zhidkov.cli.commandArgsParsers.CommandArgParsingException;
import ru.spbau.zhidkov.cli.commandArgsParsers.grep.GrepArgParserFabric;
import ru.spbau.zhidkov.cli.commandArgsParsers.grep.GrepArgParsingResult;
import ru.spbau.zhidkov.cli.commandArgsParsers.grep.GrepArgsParser;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;


public class GrepArgsParserTest {

    private GrepArgsParser parser = new GrepArgParserFabric().create();

    @Test
    public void noParameters() throws CommandArgParsingException {
        final GrepArgParsingResult res = parser.parse(Collections.singletonList("pattern"));
        assertFalse(res.isWordRegexp());
        assertFalse(res.isIgnoreCase());
        assertNull(res.getAfterContextNum());
        assertEquals("pattern", res.getPattern());
        assertNull(res.getFile());
    }

    @Test
    public void testIgnoreCaseTrue() throws CommandArgParsingException {
        final GrepArgParsingResult res = parser.parse(Arrays.asList("-i", "pattern"));
        assertTrue(res.isIgnoreCase());
    }

    @Test
    public void testWordRegexpTrue() throws CommandArgParsingException {
        final GrepArgParsingResult res = parser.parse(Arrays.asList("-w", "pattern"));
        assertTrue(res.isWordRegexp());
    }

    @Test
    public void testAfterContext() throws CommandArgParsingException {
        final GrepArgParsingResult res = parser.parse(Arrays.asList("-A", "5", "pattern"));
        assertEquals(Integer.valueOf(5), res.getAfterContextNum());
    }

    @Test
    public void testFile() throws CommandArgParsingException {
        final GrepArgParsingResult res = parser.parse(Arrays.asList("pattern", "file"));
        assertEquals("file", res.getFile());
    }

    @Test(expected = CommandArgParsingException.class)
    public void testAfterContextNegativeNum() throws CommandArgParsingException {
        parser.parse(Arrays.asList("-A", "-5", "pattern"));
    }

    @Test(expected = CommandArgParsingException.class)
    public void testAfterContextWithoutNum() throws CommandArgParsingException {
        parser.parse(Arrays.asList("-A", "pattern"));
    }

    @Test(expected = CommandArgParsingException.class)
    public void testAfterContextIncorrectOrder() throws CommandArgParsingException {
        parser.parse(Arrays.asList("-5", "-A", "pattern"));
    }

}
