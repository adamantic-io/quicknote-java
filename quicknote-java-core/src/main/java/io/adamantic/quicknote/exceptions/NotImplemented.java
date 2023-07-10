/*
 * Copyright (c) 2023 by Adamantic S.r.l.
 * This file is part of a software library licensed under the GNU Lesser General Public License (LGPL) version 3.
 * Please refer to the `LICENSE` file contained in the project root directory for more information.
 */

package io.adamantic.quicknote.exceptions;

import lombok.Getter;

public class NotImplemented extends SystemException {

    @Getter
    String name;

    public NotImplemented(String name) {
        super(ExceptionCode.FTR_NOTIMPL, "Not implemented");
        this.name = name;
    }
}
