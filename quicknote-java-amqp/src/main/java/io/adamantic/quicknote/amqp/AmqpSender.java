/*
 * Copyright (c) 2023 by Adamantic S.r.l.
 * This file is part of a software library licensed under the GNU Lesser General Public License (LGPL) version 3.
 * Please refer to the `LICENSE` file contained in the project root directory for more information.
 */

package io.adamantic.quicknote.amqp;

import com.rabbitmq.client.AMQP;
import io.adamantic.quicknote.QuicknoteConfig;
import io.adamantic.quicknote.Sender;
import io.adamantic.quicknote.types.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.lang3.StringUtils;

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
        if (StringUtils.isNotBlank(n.routing())) {
            destName += "." + n.routing();
            if (log.isTraceEnabled()) {
                log.trace("Routing key is not blank, appending to destination name, result: {}", destName);
            }
        }
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
