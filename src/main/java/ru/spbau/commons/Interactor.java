package ru.spbau.commons;

import ru.spbau.Msg;


public interface Interactor {

    Msg read();

    void write(Msg msg);

    void close();

    boolean isClosed();
}
