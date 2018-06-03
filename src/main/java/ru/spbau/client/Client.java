package ru.spbau.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.spbau.Msg;
import ru.spbau.MessengerServiceGrpc;
import ru.spbau.commons.AbstractPeer;
import ru.spbau.commons.Interactor;

import java.util.concurrent.TimeUnit;

/**
 * Client class (peer that should connect to server)
 */
public class Client extends AbstractPeer {

    private final static Logger LOG = LogManager.getLogger(Client.class);

    private final ManagedChannel channel;
    private final MessengerServiceGrpc.MessengerServiceStub stub;


    public Client(String host, int port, Interactor interactor) {
        super(interactor);
        LOG.trace("client starts with host=" + host + ",port=" + String.valueOf(port));
        final ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                                            .usePlaintext(true)
                                            .build();
        this.channel = channel;
        this.stub = MessengerServiceGrpc.newStub(channel);
    }

    /**
     * Connects to the server
     */
    public void connect() {
        LOG.debug("client connect");
        interlocutorPeerStream = stub.startChat(new StreamObserver<Msg>() {

            @Override
            public void onNext(Msg value) {
                LOG.trace("new message from server");
                interactor.write(value);
            }

            @Override
            public void onError(Throwable t) {
                LOG.error("client onError", t);
                shutdown();
            }

            @Override
            public void onCompleted() {
                LOG.debug("client onCompleted");
                shutdown();
            }
        });
    }

    /**
     * starts client
     */
    public void run() {
        LOG.debug("client run");

        startPeerCycle();
        shutdown();

    }

    /**
     * terminates client's workflow
     */
    public void shutdown()  {
        try {
            LOG.debug("client shutdown");
            isShutdown = true;
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
            interactor.close();
        } catch (InterruptedException e) {
            LOG.error("error during client shutdown", e);
        }
    }


}
