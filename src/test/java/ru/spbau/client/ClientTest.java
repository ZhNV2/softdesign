package ru.spbau.client;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.spbau.MessengerServiceGrpc;
import ru.spbau.Msg;
import ru.spbau.commons.Interactor;
import ru.spbau.commons.TestConstants;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class ClientTest {

    private Server server;
    private boolean connected;

    @Before
    public void startServer() throws InterruptedException, IOException {
        connected = false;
        server = ServerBuilder.forPort(TestConstants.SERVER_PORT)
                .addService(new ServiceImpl())
                .build();
        server.start();

    }

    @After
    public void stopServer() {
        server.shutdown();
    }

    @Test
    public void testMessageFromClientWasReceived() throws InterruptedException {
        final Interactor interactorMock = mock(Interactor.class);
        final Client client = new Client(TestConstants.SERVER_HOST, TestConstants.SERVER_PORT, interactorMock);
        when(interactorMock.read()).thenReturn(TestConstants.DEFAULT_MESSAGE).thenReturn(null);
        when(interactorMock.isClosed()).thenReturn(false).thenReturn(true);
        client.connect();
        while (!connected) {
            Thread.sleep(100);
        }
        client.run();
        verify(interactorMock, times(2)).write(TestConstants.DEFAULT_MESSAGE);
        client.shutdown();
    }

    private class ServiceImpl extends MessengerServiceGrpc.MessengerServiceImplBase {

        @Override
        public StreamObserver<Msg> startChat(StreamObserver<Msg> responseObserver) {
            connected = true;
            return new StreamObserver<Msg>() {

                @Override
                public void onNext(Msg value) {
                    responseObserver.onNext(value);
                }

                @Override
                public void onError(Throwable t) {

                }

                @Override
                public void onCompleted() {
                }
            };
        }
    }
}
