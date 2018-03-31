package ru.spbau.zhidkov.cli;

import ru.spbau.zhidkov.cli.command.*;
import ru.spbau.zhidkov.environment.Environment;
import ru.spbau.zhidkov.environment.EnvironmentImpl;
import ru.spbau.zhidkov.parser.CommandCall;
import ru.spbau.zhidkov.parser.ParseException;
import ru.spbau.zhidkov.parser.Parser;

import java.io.IOException;
import java.util.*;

/**
 * Command line interpreter.
 * Executes commands one by one, supporting substitutions ($val) and pipes (|)
 */
public class Cli {

    private final Map<String, Command> commands;
    private final Environment<String, String> environment;
    private final Parser parser;

    public Cli() {
        commands = buildCommands();
        environment = new EnvironmentImpl<>();
        parser = new Parser();
    }

    private Map<String, Command> buildCommands() {
        final Map<String, Command> commandMap = new HashMap<>();
        commandMap.put("=", new AssignmentCommand());
        commandMap.put("cat", new CatCommand());
        commandMap.put("echo", new EchoCommand());
        commandMap.put("exit", new ExitCommand());
        commandMap.put("wc", new WcCommand());
        commandMap.put("pwd", new PwdCommand());
        return Collections.unmodifiableMap(commandMap);
    }

    /**
     * Starts cli. Executes commands in cycle until exit command will be typed
     */
    public void start() {
        final Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print(">>");
            final String textLine = scanner.nextLine();
            List<CommandCall> commandCalls;
            try {
                commandCalls = parser.process(environment, textLine);
            } catch (ParseException e) {
                System.out.println("there is an error in command syntax: " + e.getMessage());
                continue;
            }
            List<String> lines = Collections.emptyList();
            boolean isExit = false;
            for (CommandCall commandCall : commandCalls) {
                final Command command = commands.get(commandCall.getCommand());
                CommandResult commandResult;
                try {
                    commandResult = command.execute(lines, commandCall.getArgs(), environment);
                } catch (IOException e) {
                    System.out.println("exception during execution");
                    System.out.println(e.getClass().getName() + ": " + e.getMessage());
                    break;
                }
                lines = commandResult.getOutput();
                if (commandResult.isExit()) {
                    isExit = true;
                    break;
                }
            }
            lines.forEach(System.out::println);
            if (isExit) {
                break;
            }
        }
    }
}
