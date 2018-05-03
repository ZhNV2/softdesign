package ru.spbau.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import ru.spbau.Msg;
import ru.spbau.RouteGuideGrpc;

import java.io.IOException;

public class MyServer {

    private Server server;

    private void start() throws IOException {
    /* The port on which the server should run */
        int port = 5000;
        this.server = ServerBuilder.forPort(port)
                .addService(new GreeterImpl())
                .build()
                .start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Use stderr here since the logger may have been reset by its JVM shutdown hook.
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            MyServer.this.stop();
            System.err.println("*** server shut down");
        }));
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main launches the server from the command line.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        final MyServer myServer = new MyServer();
        myServer.start();
        myServer.blockUntilShutdown();
    }

    static class GreeterImpl extends RouteGuideGrpc.RouteGuideImplBase {

        @Override
        public void getFeature(ru.spbau.Msg request,
                               io.grpc.stub.StreamObserver<ru.spbau.Msg> responseObserver) {
            final String res = request.getFoo() + " resp";
            responseObserver.onNext(Msg.newBuilder().setFoo(res).build());
            responseObserver.onCompleted();
        }

    }


}
