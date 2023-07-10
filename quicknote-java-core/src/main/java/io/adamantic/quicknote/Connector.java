/*
 * Copyright (c) 2023 by Adamantic S.r.l.
 * This file is part of a software library licensed under the GNU Lesser General Public License (LGPL) version 3.
 * Please refer to the `LICENSE` file contained in the project root directory for more information.
 */

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

