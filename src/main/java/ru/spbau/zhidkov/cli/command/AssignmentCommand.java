package ru.spbau.zhidkov.cli.command;

import ru.spbau.zhidkov.cli.commandArgsParsers.CommandArgParsingException;
import ru.spbau.zhidkov.environment.Environment;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Class for = command
 */
public class AssignmentCommand implements Command {

    /**
     * Sets first argument value to second argument
     * @param input Lines of text from previous command execution (before pipe)
     * @param args arguments were provided for this command
     * @param environment environment
     */
    @Override
    public CommandResult execute(List<String> input, List<String> args, Environment<String, String> environment) throws IOException, CommandArgParsingException {
        if (args.size() < 2) {
            throw new IllegalStateException("assignment should have 2 parameters");
        }
        environment.set(args.get(0), args.get(1));
        return new CommandResult(Collections.emptyList(), false);
    }
}
