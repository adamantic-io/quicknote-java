package io.adamantic.quicknote.amqp;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.adamantic.quicknote.Connector;
import io.adamantic.quicknote.QuicknoteConfig;
import io.adamantic.quicknote.Receiver;
import io.adamantic.quicknote.Sender;
import io.adamantic.quicknote.exceptions.ChannelNotFound;
import io.adamantic.quicknote.exceptions.NotImplemented;
import io.adamantic.quicknote.types.ChannelState;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.adamantic.quicknote.QuicknoteConfig.requireStringNotEmpty;

/**
 * Connector implementation for the AMQP (RabbitMQ) protocol
 */
@Slf4j
public class AmqpConnector implements Connector {

    @Getter
    private String url;
    private QuicknoteConfig config;
    private Connection connection;
    private final Map<String, AmqpSender> senders = new HashMap<>();

    private final Map<String, AmqpReceiver> receivers = new HashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public String name() {
        return "amqp";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(QuicknoteConfig c) {
        config = c;
        var connConfig = c.configForConnector(name());
        url = requireStringNotEmpty(connConfig, "url");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void open() throws IOException {
        log.debug("Connecting to AMQP broker at {}", url);
        ConnectionFactory factory = new ConnectionFactory();
        try {
            factory.setUri(url);
            connection = factory.newConnection();
            log.info("Connected to AMQP broker at {}", url);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public ChannelState state() {
        if (connection != null && connection.isOpen()) return ChannelState.OPEN;
        return ChannelState.CLOSED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Sender sender(String name) throws ChannelNotFound {
        synchronized (senders) {
            var sender = senders.get(name);
            if (sender != null) {
                if (sender.state() == ChannelState.OPEN) {
                    return sender;
                }
                log.warn("Sender {} is in state {}, reopening", name, sender.state());
                sender.close();
            }
            try {
                sender = new AmqpSender(name, this, config);
                sender.open();
                senders.put(name, sender);
                return sender;
            } catch (Exception e) {
                throw new ChannelNotFound(name, e);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Receiver receiver(String name) throws ChannelNotFound {
        synchronized (receivers) {
            var receiver = receivers.get(name);
            if (receiver != null) {
                if (receiver.state == ChannelState.OPEN) {
                    return receiver;
                }
                log.warn("Receiver {} is in state {}, reopening", name, receiver.state);
                receiver.close();
            }
            try {
                receiver = new AmqpReceiver(name, this, config);
                receiver.open();
                receivers.put(name, receiver);
                return receiver;
            } catch (Exception e) {
                throw new ChannelNotFound(name, e);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        synchronized (senders) {
            senders.values().forEach(AmqpSender::close);
            senders.clear();
        }
        synchronized (receivers) {
            receivers.values().forEach(AmqpReceiver::close);
            receivers.clear();
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (IOException e) {
                log.warn("Error closing AMQP connection", e);
            }
        }

    }


    /**
     * Creates a new channel on the AMQP connection to be used by senders or receivers.
     * Access is package-private to allow senders and receivers to spawn channels - not
     * intended for use by client code.
     *
     * @return a new channel, ready for use
     * @throws IOException if the connection is not open or if the channel cannot be created
     */
    Channel spawnChannel() throws IOException {
        if (connection == null || !connection.isOpen()) throw new IOException("Connection is not open");
        return connection.createChannel();
    }

}
