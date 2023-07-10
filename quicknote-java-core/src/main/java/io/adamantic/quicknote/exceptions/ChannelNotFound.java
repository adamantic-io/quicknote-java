/*
 * Copyright (c) 2023 by Adamantic S.r.l.
 * This file is part of a software library licensed under the GNU Lesser General Public License (LGPL) version 3.
 * Please refer to the `LICENSE` file contained in the project root directory for more information.
 */

package io.adamantic.quicknote.exceptions;

import lombok.Getter;

/**
 * Exception thrown when a channel is not found.
 * @author Domenico Barra - domenico@adamantic.io
 */
public class ChannelNotFound extends BusinessException {

    /**
     * The name of the channel that was not found.
     */
    @Getter
    String name;

    /**
     * Constructor specifying the name of the channel that was not found.
     * @param name the name of the channel that was not found.
     */
    public ChannelNotFound(String name) {
        super(ExceptionCode.CHN_NOTFOUND, "Channel not found: " + name);
        this.name = name;
    }

    /**
     * Constructor specifying the name of the channel that was not found and the cause
     * of lookup failure.
     * @param name the name of the channel that was not found.
     * @param cause the cause of lookup failure.
     */
    public ChannelNotFound(String name, Throwable cause) {
        super(ExceptionCode.CHN_NOTFOUND, "Channel not found: " + name, cause);
        this.name = name;
    }
}
