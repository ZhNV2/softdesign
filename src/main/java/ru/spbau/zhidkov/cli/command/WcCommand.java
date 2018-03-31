package ru.spbau.zhidkov.cli.command;

import ru.spbau.zhidkov.cli.commandArgsParsers.CommandArgParsingException;
import ru.spbau.zhidkov.environment.Environment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * Class for wc command
 */
public class WcCommand implements Command {

    /**
     * Counts number of lines, words and bytes
     * @param input Lines of text from previous command execution (before pipe)
     * @param args arguments were provided for this command
     * @param environment environment
     */
    @Override
    public CommandResult execute(List<String> input, List<String> args, Environment<String, String> environment) throws IOException, CommandArgParsingException {
        final List<String> inputLines = args.size() > 0
                                        ? Files.readAllLines(Paths.get(args.get(0)))
                                        : input;
        int wordsCnt = 0;
        int bytesCnt = 0;
        for (String line : inputLines) {
            wordsCnt += Stream.of(line.split(" ")).filter(s -> s.length() != 0).count();
            bytesCnt += line.getBytes().length;
        }
        return new CommandResult(Collections.singletonList(inputLines.size() + " " + wordsCnt + " " + bytesCnt), false);
    }

}
