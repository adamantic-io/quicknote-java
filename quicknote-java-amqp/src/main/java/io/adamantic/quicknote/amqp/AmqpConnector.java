package io.adamantic.quicknote.amqp;

import io.adamantic.quicknote.Connector;
import io.adamantic.quicknote.Receiver;
import io.adamantic.quicknote.Sender;
import io.adamantic.quicknote.exceptions.ChannelNotFound;
import io.adamantic.quicknote.exceptions.NotImplemented;
import io.adamantic.quicknote.types.ChannelState;
import org.apache.commons.configuration2.HierarchicalConfiguration;
import org.apache.commons.configuration2.tree.ImmutableNode;

/**
 * Connector implementation for the AMQP (RabbitMQ) protocol
 */
public class AmqpConnector implements Connector {

    @Override
    public String name() {
        return "amqp";
    }

    @Override
    public void initialize(HierarchicalConfiguration<ImmutableNode> configuration) {

    }

    @Override
    public void open() {

    }

    @Override
    public ChannelState state() {
        return ChannelState.CLOSED;
    }

    @Override
    public Sender sender(String name) throws ChannelNotFound {
        return null;
    }

    @Override
    public Receiver receiver(String name) throws ChannelNotFound {
        throw new NotImplemented("AmqpConnector.getReceiver(String)");
    }

    @Override
    public void close() {

    }

}
