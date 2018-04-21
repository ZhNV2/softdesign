package ru.spbau.zhidkov.cli.commandArgsParsers.grep.impls.jcommander;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

import java.util.List;

public class GrepArgsDescription {

    @Parameter(names = {"-i"}, description = "Perform case insensitive matching. By default, grep is case sensitive.")
    private boolean ignoreCase = false;

    @Parameter(names = {"-w"}, description = "The expression is searched for as a word ")
    private boolean wordRegexp = false;

    @Parameter(names = {"-A"}, description = "Print num lines of trailing context after each match.",
            validateWith = PositiveInteger.class)
    private Integer afterContextNum;

    @Parameter(description = "Pattern and file to grep", validateWith = MainArgsValidator.class)
    private List<String> mainArgs;

    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    public boolean isWordRegexp() {
        return wordRegexp;
    }

    public Integer getAfterContextNum() {
        return afterContextNum;
    }

    public List<String> getMainArgs() {
        return mainArgs;
    }

    public static class PositiveInteger implements IParameterValidator {
        public void validate(String name, String value) throws ParameterException {
            int n = Integer.parseInt(value);
            if (n < 0) {
                throw new ParameterException("Parameter " + name + " should be positive (found " + value +")");
            }
        }
    }

    public static class MainArgsValidator implements IParameterValidator {
        public void validate(String name, String value) throws ParameterException {
            int argsCnt = value.split(" ").length;
            if (argsCnt == 0) {
                throw new ParameterException("No pattern was specified");
            } else if (argsCnt > 2) {
                throw new ParameterException("Additional args were provided, specify only pattern and optionally file");
            }
        }
    }

}
