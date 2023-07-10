/*
 * Copyright (c) 2023 by Adamantic S.r.l.
 * This file is part of a software library licensed under the GNU Lesser General Public License (LGPL) version 3.
 * Please refer to the `LICENSE` file contained in the project root directory for more information.
 */

package io.adamantic.quicknote.amqp;


import io.adamantic.quicknote.Quicknote;

import static org.junit.jupiter.api.Assertions.*;

class PluginTest {



    @org.junit.jupiter.api.Test
    void connector() {
        assertNotNull(Quicknote.instance().connector("amqp"));
    }

}
