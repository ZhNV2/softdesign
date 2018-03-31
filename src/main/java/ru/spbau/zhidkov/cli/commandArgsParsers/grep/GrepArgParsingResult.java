package ru.spbau.zhidkov.cli.commandArgsParsers.grep;


/**
 * Class presenting results of parsing grep args.
 */
public class GrepArgParsingResult {

    private boolean ignoreCase;
    private boolean wordRegexp;
    private Integer afterContextNum;
    private String pattern;
    private String file;

    GrepArgParsingResult(boolean ignoreCase, boolean wordRegexp, Integer afterContextNum, String pattern, String file) {
        this.ignoreCase = ignoreCase;
        this.wordRegexp = wordRegexp;
        this.afterContextNum = afterContextNum;
        this.pattern = pattern;
        this.file = file;
    }

    /**
     * Returns {@code true} if case ignorance flag set
     * @return case ignorance flag
     */
    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    /**
     * Returns {@code true} if word regexp flag set (if it is
     * necessary to match only the whole words)
     *
     * @return case ignorance flag
     */
    public boolean isWordRegexp() {
        return wordRegexp;
    }

    /**
     * Returns the number of lines to print after matched string
     * or {@code null} if corresponding flag is not set
     *
     * @return the number of lines to print after matched string
     */
    public Integer getAfterContextNum() {
        return afterContextNum;
    }

    /**
     * Returns regexp
     * @return regexp
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * File to grep or {@code null} if file was not specified
     * @return file to grep
     */
    public String getFile() {
        return file;
    }
}
