package io.adamantic.quicknote;

import static org.junit.jupiter.api.Assertions.*;

class QuicknoteTest {

    @org.junit.jupiter.api.Test
    void instance() {
        assertNotNull(Quicknote.instance());
    }

    @org.junit.jupiter.api.Test
    void sender() throws Exception {
        //assertNotNull(Quicknote.instance().sender("testsender"));
    }

    @org.junit.jupiter.api.Test
    void connector() {
        //assertNotNull(Quicknote.instance().connector("amqp"));
    }

    @org.junit.jupiter.api.Test
    void config() {
        Quicknote.instance().config().globalConfig();
    }
}
