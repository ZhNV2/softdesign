package ru.spbau.zhidkov.cli.command;

import ru.spbau.zhidkov.environment.Environment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExternalCommand implements Command {
    
    private final String commandName;

    public ExternalCommand(String commandName) {
        this.commandName = commandName;
    }

    @Override
    public CommandResult execute(List<String> input, List<String> args, Environment<String, String> environment) throws IOException {
        final String bashCommand = args.stream().collect(Collectors.joining(" ", commandName + " ", ""));
        final Process process = Runtime.getRuntime().exec(bashCommand);
        final List<String> output = new ArrayList<>();
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            throw new IllegalStateException("interrupted while waiting for process finish");
        }
        final BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        while (in.ready()) {
            output.add(in.readLine());
        }
        in.close();
        return new CommandResult(output, false);
    }
}
