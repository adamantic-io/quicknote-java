/*
 * Copyright (c) 2023 by Adamantic S.r.l.
 * This file is part of a software library licensed under the GNU Lesser General Public License (LGPL) version 3.
 * Please refer to the `LICENSE` file contained in the project root directory for more information.
 */

package io.adamantic.quicknote.exceptions;

import lombok.Getter;

public class SystemException extends RuntimeException {

    @Getter
    private final ExceptionCode code;

    public SystemException(ExceptionCode code) {
        this.code = code;
    }

    public SystemException(ExceptionCode code, String message) {
        super(message);
        this.code = code;
    }

    public SystemException(ExceptionCode code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}
