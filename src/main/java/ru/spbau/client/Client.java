package ru.spbau.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import ru.spbau.Msg;
import ru.spbau.RouteGuideGrpc;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class Client {

    private final ManagedChannel channel;
    private final RouteGuideGrpc.RouteGuideFutureStub stub;

    public Client(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext(true)
                .build());
    }

    private Client(ManagedChannel channel) {
        this.channel = channel;
        this.stub = RouteGuideGrpc.newFutureStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public String send(String text) {

        final Msg msg = Msg.newBuilder().setFoo(text).build();
        final Msg response;

        try {
            response = stub.getFeature(msg).get();
            return response.getFoo();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("error");
            throw new IllegalStateException("");
        }
    }

    public static void main(String[] args) throws Exception {
        final Client client = new Client("localhost", 5000);
        try {
            System.out.println(client.send("kuku"));
        } finally {
            client.shutdown();
        }
    }

}
