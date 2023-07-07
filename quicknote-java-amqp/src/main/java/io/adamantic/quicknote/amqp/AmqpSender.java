package io.adamantic.quicknote.amqp;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import io.adamantic.quicknote.QuicknoteConfig;
import io.adamantic.quicknote.Sender;
import io.adamantic.quicknote.types.ChannelState;
import io.adamantic.quicknote.types.Message;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;

/**
 * Sends message through AMQP protocol
 */
@Slf4j
public class AmqpSender implements Sender {
    public static final String DEFAULT_DEST_TYPE = "queue";
    public static final boolean DEFAULT_DEST_DURABLE = true;

    @Getter
    final private String name;

    @Getter
    private ChannelState state;

    final private AmqpConnector connector;

    final private QuicknoteConfig config;

    private Channel channel;
    private boolean durable;
    private String destType;
    private String destName;

    AmqpSender(String name, AmqpConnector connector, QuicknoteConfig config) {
        this.name = name;
        this.connector = connector;
        this.state = ChannelState.CLOSED;
        this.config = config;
        this.initialize();
    }

    private void initialize() {
        var cfg = this.config.configForSender(name);
        this.durable = cfg.getBoolean("durable", DEFAULT_DEST_DURABLE);
        this.destType = cfg.getString("type", DEFAULT_DEST_TYPE);
        this.destName = QuicknoteConfig.requireStringNotEmpty(cfg, "dest");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        if (channel != null) {
            try {
                channel.close();
            } catch (Exception e) {
                log.warn("Error closing channel", e);
            }
            channel = null;
        }
        state = ChannelState.CLOSED;
    }

    @Override
    public void open() throws IOException {
        if (state == ChannelState.OPEN) {
            return;
        }
        channel = connector.spawnChannel();
        switch (destType) {
            case "queue" -> openQueue();
            case "topic" -> openTopic();
            default -> throw new IOException("Unknown destination type: " + destType);
        }
        state = ChannelState.OPEN;
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

    private Channel getChannel() throws IOException {
        if (channel == null) {
            throw new IOException("Channel not open");
        }
        return channel;
    }
    private void openQueue() throws IOException {
        getChannel().queueDeclare(destName, durable, false, false, null);
    }

    private void openTopic() throws IOException {
        getChannel().exchangeDeclare(destName, "topic", durable);
    }

    private void sendToTopic(Message n) throws IOException {
        getChannel().basicPublish(destName, n.routing(), buildBasicProperties(n), n.payload());
    }

    private void sendToQueue(Message n) throws IOException {
        getChannel().basicPublish("", destName, buildBasicProperties(n), n.payload());
    }

    private static AMQP.BasicProperties buildBasicProperties(Message m) {
        return new AMQP.BasicProperties.Builder()
                .headers(new HashMap<String, Object>(m.headers()))
                .messageId(String.valueOf(m.id()))
                .contentType(m.contentType())
                .build();
    }
}
