package ru.spbau.client;

import com.google.protobuf.Timestamp;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.spbau.Msg;
import ru.spbau.MessengerServiceGrpc;
import ru.spbau.commons.Cli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Client {

    private final static Logger LOG = LogManager.getLogger(Client.class);

    private final ManagedChannel channel;
    private final MessengerServiceGrpc.MessengerServiceStub stub;

    private Client(String host, int port) {
        final ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                                            .usePlaintext(true)
                                            .build();
        this.channel = channel;
        this.stub = MessengerServiceGrpc.newStub(channel);
    }

    private void shutdown()  {
        try {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void start(String user) {
        final Cli cli = new Cli(user);
        final List<StreamObserver<Msg>> stream = new ArrayList<>();
        stream.add(null);
        stream.set(0, stub.startChat(new StreamObserver<Msg>() {

            @Override
            public void onNext(Msg value) {
                cli.write(value);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("client error");
                shutdown();
                cli.stop();
            }

            @Override
            public void onCompleted() {
                System.out.println("client compl");
                shutdown();
                cli.stop();
            }
        }));
        cli.start(stream.get(0));
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 3) {
            System.out.println("usage: ./client host port login");
            LOG.error("incorrect command line args");
            return;
        }
        final String host = args[0];
        final int port = Integer.valueOf(args[1]);
        final String login = args[2];

        final Client client = new Client(host, port);
        client.start(login);
    }

}
