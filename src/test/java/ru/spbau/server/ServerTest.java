package ru.spbau.server;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.Test;
import ru.spbau.MessengerServiceGrpc;
import ru.spbau.Msg;
import ru.spbau.commons.Interactor;
import ru.spbau.commons.TestConstants;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ServerTest {

    @Test
    public void testMessageFromServerWasReceived() {
        final Interactor interactorMock = mock(Interactor.class);
        when(interactorMock.read()).thenReturn(TestConstants.DEFAULT_MESSAGE);
        final MyServer myServer = new MyServer(TestConstants.SERVER_PORT, interactorMock);
        new Thread(myServer::run).start();

        final ManagedChannel channel = ManagedChannelBuilder.forAddress(TestConstants.SERVER_HOST, TestConstants.SERVER_PORT)
                .usePlaintext(true)
                .build();
        final MessengerServiceGrpc.MessengerServiceStub stub = MessengerServiceGrpc.newStub(channel);

        final Msg[] msgs = new Msg[1];
        stub.startChat(new StreamObserver<Msg>() {
            @Override
            public void onNext(Msg value) {
                msgs[0] = value;
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {

            }
        });
        while (msgs[0] == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertEquals(TestConstants.DEFAULT_MESSAGE, msgs[0]);
        myServer.shutdown();
    }


}
