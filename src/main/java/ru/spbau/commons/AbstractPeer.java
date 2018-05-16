package ru.spbau.commons;

import io.grpc.stub.StreamObserver;
import ru.spbau.Msg;

public class AbstractPeer {

    protected final Interactor interactor;

    protected StreamObserver<Msg> interlocutorPeerStream;
    protected boolean isShutdown = false;

    protected AbstractPeer(Interactor interactor) {
        this.interactor = interactor;
    }

    protected void startPeerCycle() {
        while (!interactor.isClosed() && !isShutdown) {
            final Msg msg = interactor.read();
            if (msg == null) {
                continue;
            }
            interactor.write(msg);
            if (interlocutorPeerStream != null) {
                interlocutorPeerStream.onNext(msg);
            }
        }
    }
}
