/*
 * Copyright (c) 2023 by Adamantic S.r.l.
 * This file is part of a software library licensed under the GNU Lesser General Public License (LGPL) version 3.
 * Please refer to the `LICENSE` file contained in the project root directory for more information.
 */

package io.adamantic.quicknote.types;

/**
 * Simple representation of the state of a channel
 * @author Domenico Barra - domenico@adamantic.io
 */
public enum ChannelState {
    /**
     * The channel is closed, it cannot transport data.
     */
    CLOSED,

    /**
     * The channel is open, it can be used to communicate.
     */
    OPEN,

    /**
     * The channel is in an error state, it cannot transport data until some remediation
     * action is taken (e.g. closing and reopening, or other repair actions).
     */
    ERROR
}
