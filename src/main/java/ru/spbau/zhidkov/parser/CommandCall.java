package ru.spbau.zhidkov.parser;

import java.util.List;

/**
 * Class describing command call (main usage - result of parsing command line
 * input). Consists of two fields, command name and arguments.
 */
public class CommandCall {

    private final String command;
    private final List<String> args;

    public CommandCall(String command, List<String> args) {
        this.command = command;
        this.args = args;
    }

    public String getCommand() {
        return command;
    }

    public List<String> getArgs() {
        return args;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommandCall that = (CommandCall) o;

        if (command != null ? !command.equals(that.command) : that.command != null) return false;
        return args != null ? args.equals(that.args) : that.args == null;
    }

    @Override
    public int hashCode() {
        int result = command != null ? command.hashCode() : 0;
        result = 31 * result + (args != null ? args.hashCode() : 0);
        return result;
    }
}
