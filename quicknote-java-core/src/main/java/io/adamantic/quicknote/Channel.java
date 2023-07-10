/*
 * Copyright (c) 2023 by Adamantic S.r.l.
 * This file is part of a software library licensed under the GNU Lesser General Public License (LGPL) version 3.
 * Please refer to the `LICENSE` file contained in the project root directory for more information.
 */

package io.adamantic.quicknote;

import io.adamantic.quicknote.types.ChannelState;

import java.io.IOException;

/**
 * Represents a channel for sending or receiving messages.
 * While nothing prevents having full-duplex channels, we generally find
 * it more useful to have separate channels for sending and receiving messages.
 * This interface is used to abstract the underlying transport protocol.
 * @author Domenico Barra - domenico@adamantic.io
 */
public interface Channel extends AutoCloseable {

    /**
     * The name of the channel. This is used to identify the channel in the system,
     * configuration, and logs.
     * @return the name of the channel.
     */
    String name();

    /**
     * Opens the channel for sending or receiving messages.
     * @throws IOException if the channel cannot be opened.
     */
    void open() throws IOException;

    /**
     * Closes the channel.
     * It is a requirement that this method be idempotent and not throw any exceptions.
     */
    void close();

    /**
     * Returns the state of the channel.
     * @return the state of the channel.
     */
    ChannelState state();
}
