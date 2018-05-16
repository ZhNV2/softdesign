package ru.spbau.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.spbau.MessengerServiceGrpc;
import ru.spbau.Msg;
import ru.spbau.commons.AbstractPeer;
import ru.spbau.commons.Interactor;

public class MyServer extends AbstractPeer {

    private final static Logger LOG = LogManager.getLogger(MyServer.class);

    private final Server server;

    public MyServer(int port, Interactor interactor) {
        super(interactor);
        LOG.trace("server initialized with port=" + port);
        this.server = ServerBuilder.forPort(port)
                .addService(new ServiceImpl())
                .build();
    }

    public void run()  {
        LOG.debug("server run");
        try {
            server.start();
            startPeerCycle();
            shutdown();
        } catch (Exception e) {
            LOG.error("error during server run", e);
        }
    }

    public void shutdown() {
        LOG.debug("server shutdown");
        isShutdown = true;
        server.shutdown();
    }



    private class ServiceImpl extends MessengerServiceGrpc.MessengerServiceImplBase {

        @Override
        public StreamObserver<Msg> startChat(StreamObserver<Msg> responseObserver) {
            interlocutorPeerStream = responseObserver;
            return new StreamObserver<Msg>() {

                @Override
                public void onNext(Msg value) {
                    LOG.trace("new message from client");
                    interactor.write(value);
                }

                @Override
                public void onError(Throwable t) {
                    LOG.error("server onError", t);
                    shutdown();
                }

                @Override
                public void onCompleted() {
                    LOG.debug("server onCompleted");
                    shutdown();
                }
            };
        }
    }


}
