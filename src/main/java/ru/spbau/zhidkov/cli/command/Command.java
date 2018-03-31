package ru.spbau.zhidkov.cli.command;

import ru.spbau.zhidkov.cli.commandArgsParsers.CommandArgParsingException;
import ru.spbau.zhidkov.environment.Environment;

import java.io.IOException;
import java.util.List;

/**
 * Every supported command should implement this interface
 */
public interface Command {

    /**
     * Executes command with given arguments in the given environment
     *
     * @param input Lines of text from previous command execution (before pipe)
     * @param args arguments were provided for this command
     * @param environment environment
     * @return result of command execution
     * @throws IOException if something went wrong with file system
     */
    CommandResult execute(List<String> input, List<String> args, Environment<String, String> environment) throws IOException, CommandArgParsingException;

}
