package io.adamantic.quicknote;

import io.adamantic.quicknote.types.ChannelState;

import java.io.IOException;

public interface Channel extends AutoCloseable {
    String name();
    void open() throws IOException;
    void close();

    ChannelState state();
}
