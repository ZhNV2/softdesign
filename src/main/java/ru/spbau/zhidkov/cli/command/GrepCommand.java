package ru.spbau.zhidkov.cli.command;

import ru.spbau.zhidkov.cli.commandArgsParsers.CommandArgParsingException;
import ru.spbau.zhidkov.cli.commandArgsParsers.grep.GrepArgParserFabric;
import ru.spbau.zhidkov.cli.commandArgsParsers.grep.GrepArgParsingResult;
import ru.spbau.zhidkov.cli.commandArgsParsers.grep.GrepArgsParser;
import ru.spbau.zhidkov.environment.Environment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for grep command
 */
public class GrepCommand implements Command {

    private final GrepArgParserFabric grepArgParserFabric = new GrepArgParserFabric();

    /**
     * Searches for lines that matches given regexp.
     * Have several command line args, -i for case ignorance,
     * -w for matching only whole words, -A num for printing num lines
     * after matched one.
     *
     * @param input Lines of text from previous command execution (before pipe)
     * @param args arguments were provided for this command
     * @param environment environment
     */
    @Override
    public CommandResult execute(List<String> input, List<String> args, Environment<String, String> environment) throws IOException, CommandArgParsingException {
        final GrepArgsParser argsParser = grepArgParserFabric.create();
        final GrepArgParsingResult grepOptions = argsParser.parse(args);
        int flags = grepOptions.isIgnoreCase() ? Pattern.CASE_INSENSITIVE : 0;
        String pattern = grepOptions.getPattern();
        if (grepOptions.isWordRegexp()) {
            pattern = "\\b" + pattern + "\\b";
        }
        final List<String> lines = grepOptions.getFile() == null
                                   ? input
                                   : Files.readAllLines(Paths.get(grepOptions.getFile()));
        final List<String> resultLines = new ArrayList<>();
        final Pattern regexPattern = Pattern.compile(pattern, flags);
        int pos = 0;
        while (pos < lines.size()) {
            final String line = lines.get(pos);
            final Matcher matcher = regexPattern.matcher(line);
            if (matcher.find()) {
                resultLines.add(line);
                if (grepOptions.getAfterContextNum() != null) {
                    for (int j = 0; j < grepOptions.getAfterContextNum(); j++) {
                        pos++;
                        if (pos >= lines.size()) {
                            break;
                        }
                        resultLines.add(lines.get(pos));
                    }
                }
            }
            pos++;
        }
        return new CommandResult(resultLines, false);
    }

}
