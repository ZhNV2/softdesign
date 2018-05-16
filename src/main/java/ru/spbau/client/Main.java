package ru.spbau.client;

import ru.spbau.commons.Cli;
import ru.spbau.commons.Interactor;

public class Main {

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("usage: ./client host port login");
            return;
        }
        final String host = args[0];
        final int port = Integer.valueOf(args[1]);
        final String login = args[2];

        final Interactor interactor = new Cli(login);
        final Client client = new Client(host, port, interactor);
        client.connect();
        client.run();
    }
}
