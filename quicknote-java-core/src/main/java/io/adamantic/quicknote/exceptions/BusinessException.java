/*
 * Copyright (c) 2023 by Adamantic S.r.l.
 * This file is part of a software library licensed under the GNU Lesser General Public License (LGPL) version 3.
 * Please refer to the `LICENSE` file contained in the project root directory for more information.
 */

package io.adamantic.quicknote.exceptions;

import lombok.Getter;

/**
 * Base class for business exceptions - i.e. all exceptions that the application
 * should be prepared to handle as a business failure.
 * Most times, business exceptions indicate that the failure condition is permanent
 * if reparatory actions are not taken, and thus the application should not retry without
 * having a remediation strategy.
 * @author Domenico Barra - domenico@adamantic.io
 */
public class BusinessException extends Exception {

    /**
     * The exception code - useful for M2M communication.
     */
    @Getter
    private final ExceptionCode code;

    /**
     * Constructs a new business exception with the given code.
     * @param code the exception code.
     */
    public BusinessException(ExceptionCode code) {
        this.code = code;
    }


    /**
     * Constructs a new business exception with the given code and message.
     * @param code the exception code.
     * @param message the exception message.
     */
    public BusinessException(ExceptionCode code, String message) {
        super(message);
        this.code = code;
    }


    /**
     * Constructs a new business exception with the given code, message and cause.
     * @param code the exception code.
     * @param message the exception message.
     * @param cause the exception that has caused this business exception.
     */
    public BusinessException(ExceptionCode code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}
