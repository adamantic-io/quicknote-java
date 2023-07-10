/*
 * Copyright (c) 2023 by Adamantic S.r.l.
 * This file is part of a software library licensed under the GNU Lesser General Public License (LGPL) version 3.
 * Please refer to the `LICENSE` file contained in the project root directory for more information.
 */

package io.adamantic.quicknote.exceptions;

import lombok.Getter;

public class ConfigException extends SystemException {


    public ConfigException(String message) {
        super(ExceptionCode.CFG_EXCEPTION, message);
    }

    public ConfigException(String message, Throwable cause) {
        super(ExceptionCode.CFG_EXCEPTION, message, cause);
    }

}
