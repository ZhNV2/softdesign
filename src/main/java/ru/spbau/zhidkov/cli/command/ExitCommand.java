package ru.spbau.zhidkov.cli.command;

import ru.spbau.zhidkov.cli.commandArgsParsers.CommandArgParsingException;
import ru.spbau.zhidkov.environment.Environment;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Class for exit command
 */
public class ExitCommand implements Command {

    /**
     * Terminates cli work cycle
     * @param input Lines of text from previous command execution (before pipe)
     * @param args arguments were provided for this command
     * @param environment environment
     */
    @Override
    public CommandResult execute(List<String> input, List<String> args, Environment<String, String> environment) throws IOException, CommandArgParsingException {
        return new CommandResult(Collections.emptyList(), true);
    }
}
