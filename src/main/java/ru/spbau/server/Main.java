package ru.spbau.server;

import ru.spbau.commons.Cli;
import ru.spbau.commons.Interactor;

/**
 * Server main
 */
public class Main {
    /**
     * starts server
     * @param args first and second should be port and login respectively
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("usage ./server port login");
            return;
        }
        final int port = Integer.valueOf(args[0]);
        final String login = args[1];
        final Interactor interactor = new Cli(login);
        final MyServer myServer = new MyServer(port, interactor);
        myServer.run();
    }
}
