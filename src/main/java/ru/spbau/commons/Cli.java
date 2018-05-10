package ru.spbau.commons;

import io.grpc.stub.StreamObserver;
import org.joda.time.DateTime;
import ru.spbau.Msg;

import java.util.Scanner;

public class Cli {

    private final String userLogin;

    private Thread thread;

    public Cli(String userLogin) {
        this.userLogin = userLogin;

    }

    public void start(StreamObserver<Msg> stream) {
        thread = new Thread(() -> {
            final Scanner scanner = new Scanner(System.in);

            while (!Thread.interrupted()) {
                if (!scanner.hasNextLine()) {
                    continue;
                }
                final String line = scanner.nextLine();
                if (line.equals("exit")) {
                    stream.onCompleted();
                    break;
                }
                final DateTime time = DateTime.now();
                final Msg msg = Msg.newBuilder()
                        .setDate(time.toString())
                        .setLogin(userLogin)
                        .setText(line)
                        .build();
                stream.onNext(msg);
                writeMessage(time, userLogin + "(you)", line);
            }
        });
        thread.start();
    }

    public void stop() {
        if (thread != null) {
            thread.interrupt();
        }
    }

    public void write(Msg msg) {
        System.out.print("\r");
        writeMessage(DateTime.parse(msg.getDate()), msg.getLogin(), msg.getText());
    }

    private void writeMessage(DateTime time, String login, String text) {
        System.out.println(String.format("<%d:%d> [%s] %s", time.hourOfDay().get(), time.minuteOfHour().get(), login, text));
    }




}
