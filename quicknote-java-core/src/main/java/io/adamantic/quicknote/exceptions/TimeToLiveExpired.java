/*
 * Copyright (c) 2023 by Adamantic S.r.l.
 * This file is part of a software library licensed under the GNU Lesser General Public License (LGPL) version 3.
 * Please refer to the `LICENSE` file contained in the project root directory for more information.
 */

package io.adamantic.quicknote.exceptions;

import io.adamantic.quicknote.types.Message;
import lombok.Getter;

/**
 * Exception thrown when a message has expired its time-to-live.
 * @author Domenico Barra - domenico@adamantic.io
 */
public class TimeToLiveExpired extends SystemException {

    /**
     * The expired message.
     */
    @Getter
    Message expiredMessage;

    /**
     * Creates a new exception instance, with a reference to the expired message.
     * @param expiredMessage the expired message
     */
    public TimeToLiveExpired(Message expiredMessage) {
        super(ExceptionCode.MSG_TTLEXP);
        this.expiredMessage = expiredMessage;
    }
}
