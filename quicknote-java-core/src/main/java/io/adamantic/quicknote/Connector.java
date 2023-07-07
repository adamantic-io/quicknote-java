package io.adamantic.quicknote;

import io.adamantic.quicknote.exceptions.ChannelNotFound;
import org.apache.commons.configuration2.HierarchicalConfiguration;
import org.apache.commons.configuration2.tree.ImmutableNode;

/**
 * Handles connection details to specific remote relays
 * Every module should have its connector.
 */
public interface Connector extends Channel {

    /**
     * Performs the connector initialization.
     * After this method returns, the connector is supposed to
     * be ready to create connections.
     * @param c the configuration object.
     */
    void initialize(QuicknoteConfig c);

    Sender sender(String name) throws ChannelNotFound;

    Receiver receiver(String name) throws ChannelNotFound;
}

