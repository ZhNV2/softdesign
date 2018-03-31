package ru.spbau.zhidkov.cli.command;

import ru.spbau.zhidkov.cli.commandArgsParsers.CommandArgParsingException;
import ru.spbau.zhidkov.environment.Environment;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for echo command
 */
public class EchoCommand implements Command {

    /**
     * Prints its arguments
     * @param input Lines of text from previous command execution (before pipe)
     * @param args arguments were provided for this command
     * @param environment environment
     */
    @Override
    public CommandResult execute(List<String> input, List<String> args, Environment<String, String> environment) throws IOException, CommandArgParsingException {
        final List<String> outputLines = args.size() > 0
                                         ? Collections.singletonList(args.stream().collect(Collectors.joining(" ")))
                                         : input;
        return new CommandResult(outputLines, false);
    }

}
