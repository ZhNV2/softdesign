package ru.spbau.zhidkov.cli.commandArgsParsers.grep;

/**
 * Fabric creating instances of <t>GrepArgsParser</t>
 */
public class GrepArgParserFabric {

    private enum Impl {
        APACHE
    }

    private Impl impl = Impl.APACHE;

    /**
     * Creates a new instance of <t>GrepArgsParser</t>
     * @return parser for grep command line args parser
     */
    public GrepArgsParser create() {
        switch (impl) {
            case APACHE:
                return new GrepArgsParser_ApacheImpl();
            default:
                throw new IllegalStateException("illegal impl");
        }
    }
}
