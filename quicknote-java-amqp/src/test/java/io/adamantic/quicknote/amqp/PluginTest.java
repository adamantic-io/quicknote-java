package io.adamantic.quicknote.amqp;


import io.adamantic.quicknote.Quicknote;

import static org.junit.jupiter.api.Assertions.*;

class PluginTest {



    @org.junit.jupiter.api.Test
    void connector() {
        assertNotNull(Quicknote.instance().connector("amqp"));
    }

}
