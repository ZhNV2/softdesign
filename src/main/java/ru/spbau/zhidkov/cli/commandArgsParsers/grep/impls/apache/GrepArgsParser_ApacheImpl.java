package ru.spbau.zhidkov.cli.commandArgsParsers.grep.impls.apache;

import org.apache.commons.cli.*;
import ru.spbau.zhidkov.cli.commandArgsParsers.CommandArgParsingException;
import ru.spbau.zhidkov.cli.commandArgsParsers.grep.GrepArgParsingResult;
import ru.spbau.zhidkov.cli.commandArgsParsers.grep.GrepArgsParser;

import java.util.Arrays;
import java.util.List;

/**
 * Implementation of <t>GrepArgsParser</t> based on apache commons cli
 */
public class GrepArgsParser_ApacheImpl implements GrepArgsParser {

    private static final String IGNORANCE_CASE = "i";
    private static final String WORD_REGEXP = "w";
    private static final String AFTER_CONTEXT = "A";

    public GrepArgParsingResult parse(List<String> args) throws CommandArgParsingException {

        final Options options = new Options();
        addArgs(options);

        final CommandLineParser parser = new DefaultParser();
        CommandLine commandLine;
        try {
            commandLine = parser.parse(options, args.toArray(new String[args.size()]));
        } catch (ParseException e) {
            throw new CommandArgParsingException(e);
        }

        final List<String> parseArgs = Arrays.asList(commandLine.getArgs());
        if (parseArgs.isEmpty()) {
            throw new CommandArgParsingException("No pattern was specified");
        }
        if (parseArgs.size() > 2) {
            throw new CommandArgParsingException("Additional args were provided, specify only pattern and optionally file");
        }
        final String pattern = parseArgs.get(0);
        final String file = parseArgs.size() > 1 ? parseArgs.get(1) : null;
        final String afterContextString = commandLine.getOptionValue(AFTER_CONTEXT);
        final Integer afterContextNum = afterContextString == null ? null : Integer.valueOf(afterContextString);
        if (afterContextNum != null && afterContextNum < 0) {
            throw new CommandArgParsingException("After context num should be non-negative");
        }
        return new GrepArgParsingResult(
                commandLine.hasOption(IGNORANCE_CASE),
                commandLine.hasOption(WORD_REGEXP),
                afterContextNum,
                pattern,
                file
        );
    }


    private void addArgs(Options options) {
        options.addOption(IGNORANCE_CASE, false, "Perform case insensitive matching. "
                + " By default, grep is case sensitive.");
        options.addOption(WORD_REGEXP, false, "The expression is searched for as a word ");
        options.addOption(Option.builder(AFTER_CONTEXT)
                .hasArg()
                .argName("num")
                .desc("Print num lines of trailing context after each match.")
                .type(int.class)
                .build());
    }

}
