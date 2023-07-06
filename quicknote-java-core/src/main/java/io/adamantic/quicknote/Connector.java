package io.adamantic.quicknote;

import io.adamantic.quicknote.exceptions.ChannelNotFound;

import java.util.Properties;

/**
 * Handles connection details to specific remote relays
 * Every module should have its connector.
 */
public interface Connector extends Channel {

    /**
     * Performs the connector initialization.
     * After this method returns, the connector is supposed to
     * be ready to create connections.
     * @param p the initialization properties (however sourced).
     */
    void initialize(Properties p);

    Sender getSender(String name) throws ChannelNotFound;

    Receiver getReceiver(String name) throws ChannelNotFound;
}

