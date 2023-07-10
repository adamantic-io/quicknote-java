/*
 * Copyright (c) 2023 by Adamantic S.r.l.
 * This file is part of a software library licensed under the GNU Lesser General Public License (LGPL) version 3.
 * Please refer to the `LICENSE` file contained in the project root directory for more information.
 */

package io.adamantic.quicknote.exceptions;

import lombok.Getter;

/**
 * Base class for all system-level exceptions.
 * These are exceptions that are not caused by the user (business) logic, but by the system itself
 * (e.g. configuration errors, network errors, etc.).
 * In some circumstances, a system exception may disappear if the operation is retried
 * (e.g. a network error may be transient) - but the application should know how to handle each
 * specific system exception.
 * @author Domenico Barra - domenico@adamantic.io
 */
public class SystemException extends RuntimeException {

    /**
     * The exception code - useful for M2M communication.
     */
    @Getter
    private final ExceptionCode code;

    /**
     * Constructs a new system exception with the given code.
     * @param code the exception code.
     */
    public SystemException(ExceptionCode code) {
        this.code = code;
    }

    /**
     * Constructs a new system exception with the given code and message.
     * @param code the exception code.
     * @param message the exception message.
     */
    public SystemException(ExceptionCode code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * Constructs a new system exception with the given code, message and cause.
     * @param code the exception code.
     * @param message the exception message.
     * @param cause the exception that has caused this system exception.
     */
    public SystemException(ExceptionCode code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}
