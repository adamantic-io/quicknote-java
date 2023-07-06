package io.adamantic.quicknote.amqp;

import io.adamantic.quicknote.Sender;
import io.adamantic.quicknote.types.ChannelState;
import io.adamantic.quicknote.types.Message;
import lombok.Getter;

/**
 * Sends message through AMQP protocol
 */
public class AmqpSender implements Sender {

    @Getter
    private String name;

    @Getter
    private ChannelState state;

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {

    }

    @Override
    public void open() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(Message n) {

    }
}
