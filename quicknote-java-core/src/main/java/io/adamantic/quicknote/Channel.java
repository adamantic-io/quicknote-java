package io.adamantic.quicknote;

import io.adamantic.quicknote.types.ChannelState;

import java.io.IOException;

public interface Channel {
    String name();
    void open() throws IOException;
    void close();

    ChannelState state();
}
