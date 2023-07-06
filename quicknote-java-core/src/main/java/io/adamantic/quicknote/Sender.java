package io.adamantic.quicknote;

import io.adamantic.quicknote.types.Message;

/**
 * A channel to send messages to a remote destination.
 */
public interface Sender extends Channel {


    /**
     * Sends a message to a remote destination.
     * Guarantees on delivery
     * @param n the message to send
     */
    void send( Message n );


}
