/*
 * Copyright (c) 2023 by Adamantic S.r.l.
 * This file is part of a software library licensed under the GNU Lesser General Public License (LGPL) version 3.
 * Please refer to the `LICENSE` file contained in the project root directory for more information.
 */

package io.adamantic.quicknote.exceptions;

import lombok.Getter;

/**
 * Exception thrown when a feature is not implemented.
 * @author Domenico Barra - domenico@adamantic.io
 */
public class NotImplemented extends SystemException {

    /**
     * The name of the feature that is not implemented.
     */
    @Getter
    String name;

    /**
     * Constructor specifying the name of the feature that is not implemented.
     * @param name the name of the feature that is not implemented.
     */
    public NotImplemented(String name) {
        super(ExceptionCode.FTR_NOTIMPL, "Not implemented");
        this.name = name;
    }
}
