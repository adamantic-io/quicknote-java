package io.adamantic.quicknote.amqp;

import com.rabbitmq.client.ConnectionFactory;
import io.adamantic.quicknote.Connector;
import io.adamantic.quicknote.Receiver;
import io.adamantic.quicknote.Sender;
import io.adamantic.quicknote.exceptions.ChannelNotFound;
import io.adamantic.quicknote.exceptions.NotImplemented;

import java.util.Properties;


public class AmqpConnector implements Connector {

    @Override
    public String getName() {
        return "amqp";
    }

    @Override
    public void initialize(Properties configuration) {

    }

    @Override
    public Sender getSender(String name) throws ChannelNotFound {
        return null;
    }

    @Override
    public Receiver getReceiver(String name) throws ChannelNotFound {
        throw new NotImplemented("AmqpConnector.getReceiver(String)");
    }

    @Override
    public void close() {

    }

}
