/*
 * Copyright (c) 2023 by Adamantic S.r.l.
 * This file is part of a software library licensed under the GNU Lesser General Public License (LGPL) version 3.
 * Please refer to the `LICENSE` file contained in the project root directory for more information.
 */

package io.adamantic.quicknote;

import io.adamantic.quicknote.exceptions.ChannelNotFound;

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

    /**
     * Returns a sender with the given name.
     * A connector may perform channel pooling, lazy initialization, etc.
     * Specific policies are implementation-dependent.
     *
     * @param name the name of the sender to retrieve.
     * @return the sender, ready to operate.
     * @throws ChannelNotFound if the sender cannot be instantiated and/or is unavailable.
     */
    Sender sender(String name) throws ChannelNotFound;

    /**
     * Returns a receiver with the given name.
     * A connector may perform channel pooling, lazy initialization, etc.
     * Specific policies are implementation-dependent.
     *
     * @param name the name of the receiver to retrieve.
     * @return the receiver, ready to operate.
     * @throws ChannelNotFound if the receiver cannot be instantiated and/or is unavailable.
     */
    Receiver receiver(String name) throws ChannelNotFound;
}

