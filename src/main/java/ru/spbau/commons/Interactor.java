package ru.spbau.commons;

import ru.spbau.Msg;


/**
 * Interface encapsulating interaction with user
 */
public interface Interactor {

    /**
     * Reads message
     *
     * @return message
     */
    Msg read();

    /**
     * Writes message
     * @param msg message
     */
    void write(Msg msg);

    /**
     * Closes interactor
     */
    void close();

    /**
     * Checks if interactor closed
     * @return if interactor closed
     */
    boolean isClosed();
}
