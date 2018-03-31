package ru.spbau.zhidkov.parser;

/**
 * Class for exceptions arising during parser's workflow
 */
public class ParseException extends RuntimeException {

    public ParseException(String message) {
        super(message);
    }

}
