/*
 * Copyright (c) 2023 by Adamantic S.r.l.
 * This file is part of a software library licensed under the GNU Lesser General Public License (LGPL) version 3.
 * Please refer to the `LICENSE` file contained in the project root directory for more information.
 */

package io.adamantic.quicknote.amqp;

import io.adamantic.quicknote.Channel;
import io.adamantic.quicknote.QuicknoteConfig;
import io.adamantic.quicknote.types.ChannelState;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration2.Configuration;

import java.io.IOException;

@Slf4j
public abstract class AmqpBaseChannel implements Channel {
    public static final String DEFAULT_DEST_TYPE = "queue";
    public static final boolean DEFAULT_DEST_DURABLE = true;

    @Getter
    final protected String name;

    @Getter
    protected ChannelState state;

    final protected AmqpConnector connector;

    final protected QuicknoteConfig config;

    protected com.rabbitmq.client.Channel channel;
    protected boolean durable;
    protected String destType;
    protected String destName;

    protected AmqpBaseChannel(String name, AmqpConnector connector, QuicknoteConfig config) {
        this.name = name;
        this.connector = connector;
        this.state = ChannelState.CLOSED;
        this.config = config;
        this.initialize();
    }

    private void initialize() {
        var cfg = this.locateOwnConfig();
        this.durable = cfg.getBoolean("durable", DEFAULT_DEST_DURABLE);
        this.destType = cfg.getString("type", DEFAULT_DEST_TYPE);
        this.destName = QuicknoteConfig.requireStringNotEmpty(cfg, "dest");
        ownInitialize();
    }

    /**
     * Locate the configuration for this channel in the global configuration
     * @return the configuration for this channel
     * @throws io.adamantic.quicknote.exceptions.ConfigException if the configuration is not found
     */
    protected abstract Configuration locateOwnConfig();

    /**
     * Performs the specific initialization for this channel
     */
    protected abstract void ownInitialize();


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

    protected com.rabbitmq.client.Channel getChannel() throws IOException {
        if (channel == null) {
            throw new IOException("Channel not open");
        }
        return channel;
    }

    protected void openQueue() throws IOException {
        getChannel().queueDeclare(destName, durable, false, false, null);
    }

    protected void openTopic() throws IOException {
        getChannel().exchangeDeclare(destName, "topic", durable);
    }

}
