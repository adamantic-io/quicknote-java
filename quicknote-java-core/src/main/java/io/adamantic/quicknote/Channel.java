package io.adamantic.quicknote;

import io.adamantic.quicknote.types.ChannelState;

public interface Channel {
    String name();
    void open();
    void close();

    ChannelState state();
}
