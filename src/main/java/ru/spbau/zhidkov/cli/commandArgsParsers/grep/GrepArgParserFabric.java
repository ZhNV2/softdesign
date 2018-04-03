package ru.spbau.zhidkov.cli.commandArgsParsers.grep;

import ru.spbau.zhidkov.cli.commandArgsParsers.grep.impls.apache.GrepArgsParser_ApacheImpl;
import ru.spbau.zhidkov.cli.commandArgsParsers.grep.impls.jcommander.GrepArgsParser_JCommanderImpl;

/**
 * Fabric creating instances of <t>GrepArgsParser</t>
 */
public class GrepArgParserFabric {

    private enum Impl {
        APACHE,
        JCOMMANDER
    }

    private Impl impl = Impl.JCOMMANDER;

    /**
     * Creates a new instance of <t>GrepArgsParser</t>
     * @return parser for grep command line args parser
     */
    public GrepArgsParser create() {
        switch (impl) {
            case APACHE:
                return new GrepArgsParser_ApacheImpl();
            case JCOMMANDER:
                return new GrepArgsParser_JCommanderImpl();
            default:
                throw new IllegalStateException("illegal impl");
        }
    }
}
