/*
 * Copyright (c) 2023 by Adamantic S.r.l.
 * This file is part of a software library licensed under the GNU Lesser General Public License (LGPL) version 3.
 * Please refer to the `LICENSE` file contained in the project root directory for more information.
 */

package io.adamantic.quicknote;

import io.adamantic.quicknote.types.ChannelState;

import java.io.IOException;

public interface Channel extends AutoCloseable {
    String name();
    void open() throws IOException;
    void close();

    ChannelState state();
}
