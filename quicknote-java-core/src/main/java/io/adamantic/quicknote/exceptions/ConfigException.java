/*
 * Copyright (c) 2023 by Adamantic S.r.l.
 * This file is part of a software library licensed under the GNU Lesser General Public License (LGPL) version 3.
 * Please refer to the `LICENSE` file contained in the project root directory for more information.
 */

package io.adamantic.quicknote.exceptions;

import lombok.Getter;

/**
 * Exception thrown when a configuration error is detected.
 * @author Domenico Barra - domenico@adamantic.io
 */
public class ConfigException extends SystemException {


    /**
     * Constructs a new configuration exception with the given message.
     * @param message the exception message.
     */
    public ConfigException(String message) {
        super(ExceptionCode.CFG_EXCEPTION, message);
    }

    /**
     * Constructs a new configuration exception with the given message and cause.
     * @param message the exception message.
     * @param cause the exception that has caused this configuration exception.
     */
    public ConfigException(String message, Throwable cause) {
        super(ExceptionCode.CFG_EXCEPTION, message, cause);
    }

}
