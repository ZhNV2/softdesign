package ru.spbau.commons;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import ru.spbau.Msg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Cli implements Interactor {

    private final static Logger LOG = LogManager.getLogger(Cli.class);

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private final String login;

    private boolean closed = false;

    public Cli(String login) {
        this.login = login;
    }

    @Override
    public Msg read() {
        try {
            if (!reader.ready()) {
                return null;
            }
            final String line = reader.readLine();
            if (line.equals("exit")) {
                LOG.debug("cli exit");
                close();
                return null;
            }
            final DateTime time = DateTime.now();
            return Msg.newBuilder()
                    .setDate(time.toString())
                    .setLogin(login)
                    .setText(line)
                    .build();
        } catch (IOException e) {
            LOG.error("error during reader read", e);
            return null;
        }

    }

    public void close() {
        if (closed) {
            return;
        }
        closed = true;
        try {
            reader.close();
        } catch (IOException e) {
            LOG.error("error during reader close", e);
        }
    }


    @Override
    public void write(Msg msg) {
        writeMessage(DateTime.parse(msg.getDate()), msg.getLogin(), msg.getText());
    }

    private void writeMessage(DateTime time, String login, String text) {
        System.out.println(String.format("<%d:%d> [%s] %s", time.hourOfDay().get(), time.minuteOfHour().get(), login, text));
    }

    @Override
    public boolean isClosed() {
        return closed;
    }
}
