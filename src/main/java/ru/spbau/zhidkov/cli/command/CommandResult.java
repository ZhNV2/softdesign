package ru.spbau.zhidkov.cli.command;

import java.util.List;

/**
 * Class presenting command execution result.
 * Consisting of two fields - list of lines of output and flag if it necessary to exit
 */
public class CommandResult {

    private final List<String> output;
    private final boolean exit;

    public CommandResult(List<String> output, boolean exit) {
        this.output = output;
        this.exit = exit;
    }

    public List<String> getOutput() {
        return output;
    }

    public boolean isExit() {
        return exit;
    }
}
