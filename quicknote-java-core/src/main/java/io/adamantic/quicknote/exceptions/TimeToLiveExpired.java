/*
 * Copyright (c) 2023 by Adamantic S.r.l.
 * This file is part of a software library licensed under the GNU Lesser General Public License (LGPL) version 3.
 * Please refer to the `LICENSE` file contained in the project root directory for more information.
 */

package io.adamantic.quicknote.exceptions;

import io.adamantic.quicknote.types.Message;
import lombok.Getter;

public class TimeToLiveExpired extends SystemException {

    @Getter
    Message expiredMessage;

    public TimeToLiveExpired(Message expiredMessage) {
        super(ExceptionCode.MSG_TTLEXP);
        this.expiredMessage = expiredMessage;
    }
}
