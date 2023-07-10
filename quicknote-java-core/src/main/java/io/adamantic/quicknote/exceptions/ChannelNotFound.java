/*
 * Copyright (c) 2023 by Adamantic S.r.l.
 * This file is part of a software library licensed under the GNU Lesser General Public License (LGPL) version 3.
 * Please refer to the `LICENSE` file contained in the project root directory for more information.
 */

package io.adamantic.quicknote.exceptions;

import lombok.Getter;

public class ChannelNotFound extends BusinessException {

    @Getter
    String name;

    public ChannelNotFound(String name) {
        super(ExceptionCode.CHN_NOTFOUND, "Channel not found: " + name);
        this.name = name;
    }

    public ChannelNotFound(String name, Throwable cause) {
        super(ExceptionCode.CHN_NOTFOUND, "Channel not found: " + name, cause);
        this.name = name;
    }
}
