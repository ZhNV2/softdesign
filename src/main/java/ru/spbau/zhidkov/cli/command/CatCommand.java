package ru.spbau.zhidkov.cli.command;

import ru.spbau.zhidkov.cli.commandArgsParsers.CommandArgParsingException;
import ru.spbau.zhidkov.environment.Environment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Class for cat command
 */
public class CatCommand implements Command {

    /**
     * Just translating text from file/output from previous command
     * @param input Lines of text from previous command execution (before pipe)
     * @param args arguments were provided for this command
     * @param environment environment
     */
    @Override
    public CommandResult execute(List<String> input, List<String> args, Environment<String, String> environment) throws IOException, CommandArgParsingException {
        final List<String> outputLines = args.size() > 0
                                        ? Files.readAllLines(Paths.get(args.get(0)))
                                        : input;
        return new CommandResult(outputLines, false);
    }

}
