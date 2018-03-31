package ru.spbau.zhidkov.cli.commandArgsParsers.grep;

import ru.spbau.zhidkov.cli.commandArgsParsers.CommandArgParsingException;

import java.util.List;

public interface GrepArgsParser {

    GrepArgParsingResult parse(List<String> args) throws CommandArgParsingException;

}
