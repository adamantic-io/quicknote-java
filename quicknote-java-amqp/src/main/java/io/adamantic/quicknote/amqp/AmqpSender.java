package io.adamantic.quicknote.amqp;

import io.adamantic.quicknote.Sender;
import io.adamantic.quicknote.types.Message;
import lombok.Getter;

/**
 * Sends message through AMQP protocol
 */
public class AmqpSender implements Sender {

    @Getter
    private String name;

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(Message n) {

    }
}
