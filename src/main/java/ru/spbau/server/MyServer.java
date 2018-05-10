package ru.spbau.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import ru.spbau.MessengerServiceGrpc;
import ru.spbau.Msg;
import ru.spbau.commons.Cli;

import java.io.IOException;
import java.util.Scanner;

public class MyServer {

    private Server server;


    private MyServer(int port, String login) {
        this.server = ServerBuilder.forPort(port)
                .addService(new ServiceImpl(login))
                .build();
    }

    private void start() throws IOException, InterruptedException {
        server.start();
        server.awaitTermination();
//        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
//            // Use stderr here since the logger may have been reset by its JVM shutdown hook.
//            System.err.println("*** shutting down gRPC server since JVM is shutting down");
//            MyServer.this.stop();
//            System.err.println("*** server shut down");
//        }));
    }




    /**
     * Main launches the server from the command line.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length < 2) {
            System.out.println("usage ./server port login");
            return;
        }
        final int port = Integer.valueOf(args[0]);
        final String login = args[1];

        final MyServer myServer = new MyServer(port, login);
        myServer.start();
    }

    class ServiceImpl extends MessengerServiceGrpc.MessengerServiceImplBase {

        private final Cli cli;

        private ServiceImpl(String login) {
            super();
            this.cli = new Cli(login);
        }

        @Override
        public StreamObserver<Msg> startChat(StreamObserver<Msg> responseObserver) {
            return new StreamObserver<Msg>() {
                {
                    cli.start(responseObserver);
                }


                @Override
                public void onNext(Msg value) {
                    cli.write(value);
                }

                @Override
                public void onError(Throwable t) {
                    System.out.println("server error");
                    server.shutdown();
                    cli.stop();
                }

                @Override
                public void onCompleted() {
                    System.out.println("server compl");
                    server.shutdown();
                    cli.stop();
                }
            };
        }
    }


}
