package ru.spbau.zhidkov.cli.commandArgsParsers.grep.impls.jcommander;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import ru.spbau.zhidkov.cli.commandArgsParsers.CommandArgParsingException;
import ru.spbau.zhidkov.cli.commandArgsParsers.grep.GrepArgParsingResult;
import ru.spbau.zhidkov.cli.commandArgsParsers.grep.GrepArgsParser;

import java.util.List;

public class GrepArgsParser_JCommanderImpl implements GrepArgsParser {
    @Override
    public GrepArgParsingResult parse(List<String> args) throws CommandArgParsingException {
        final GrepArgsDescription argsDescription = new GrepArgsDescription();
        final JCommander jc = JCommander.newBuilder().addObject(argsDescription).build();
        try {
            jc.parse(args.toArray(new String[args.size()]));
        } catch (ParameterException e) {
            throw new CommandArgParsingException(e);
        }
        final String pattern = argsDescription.getMainArgs().get(0);
        final String file = argsDescription.getMainArgs().size() > 1 ? argsDescription.getMainArgs().get(1) : null;
        return new GrepArgParsingResult(
                argsDescription.isIgnoreCase(),
                argsDescription.isWordRegexp(),
                argsDescription.getAfterContextNum(),
                pattern,
                file
        );

    }
}
