/*
 * Copyright (c) 2023 by Adamantic S.r.l.
 * This file is part of a software library licensed under the GNU Lesser General Public License (LGPL) version 3.
 * Please refer to the `LICENSE` file contained in the project root directory for more information.
 */

package io.adamantic.quicknote.exceptions;

/**
 * An enumeration of exception codes that facilitates
 * communication of error conditions across different
 * layers and components of a distributed system.
 * This is not meant to be used directly with base exceptions:
 * its values are injected by specialized exceptions in the base
 * class and are generally available through the `getCode()` method.
 * @author Domenico Barra
 */
public enum ExceptionCode {

    /**
     * Configuration exception
     */
    CFG_EXCEPTION,

    /**
     * Feature not implemented
     */
    FTR_NOTIMPL,

    /**
     * Channel not found
     */
    CHN_NOTFOUND,

    /**
     * Message time-to-live expired
     */
    MSG_TTLEXP

}
