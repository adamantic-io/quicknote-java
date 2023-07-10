/*
 * Copyright (c) 2023 by Adamantic S.r.l.
 * This file is part of a software library licensed under the GNU Lesser General Public License (LGPL) version 3.
 * Please refer to the `LICENSE` file contained in the project root directory for more information.
 */

package io.adamantic.quicknote;

import io.adamantic.quicknote.types.Message;

import java.io.IOException;

/**
 * A channel to send messages to a remote destination.
 * @author Domenico Barra - domenico@adamantic.io
 */
public interface Sender extends Channel {


    /**
     * Sends a message to a remote destination.
     * Guarantees on delivery
     * @param n the message to send
     * @throws IOException if the message cannot be sent
     */
    void send( Message n ) throws IOException;


}
