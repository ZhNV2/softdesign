package ru.spbau.zhidkov.cli.commandArgsParsers;

/**
 * Exceptions for command line args parsers
 */
public class CommandArgParsingException extends Exception {
    public CommandArgParsingException(String message) {
        super(message);
    }

    public CommandArgParsingException(Exception e) {
        super(e);
    }
}
