package io.adamantic.quicknote.amqp;

import com.rabbitmq.client.AMQP;
import io.adamantic.quicknote.QuicknoteConfig;
import io.adamantic.quicknote.Sender;
import io.adamantic.quicknote.types.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration2.Configuration;

import java.io.IOException;
import java.util.HashMap;

/**
 * Sends messages through AMQP protocol
 */
@Slf4j
public class AmqpSender extends AmqpBaseChannel implements Sender {

    AmqpSender(String name, AmqpConnector connector, QuicknoteConfig config) {
        super(name, connector, config);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void ownInitialize() {
        // nothing to do here
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Configuration locateOwnConfig() {
        return config.configForSender(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void send(Message n) throws IOException {
        switch (destType) {
            case "queue" -> sendToQueue(n);
            case "topic" -> sendToTopic(n);
        }
    }

    private void sendToTopic(Message n) throws IOException {
        getChannel().basicPublish(destName, n.routing(), buildBasicProperties(n), n.payload());
    }

    private void sendToQueue(Message n) throws IOException {
        getChannel().basicPublish("", destName, buildBasicProperties(n), n.payload());
    }

    private static AMQP.BasicProperties buildBasicProperties(Message m) {
        return new AMQP.BasicProperties.Builder()
                .headers(new HashMap<>(m.headers()))
                .messageId(String.valueOf(m.id()))
                .contentType(m.contentType())
                .build();
    }
}
