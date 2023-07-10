/*
 * Copyright (c) 2023 by Adamantic S.r.l.
 * This file is part of a software library licensed under the GNU Lesser General Public License (LGPL) version 3.
 * Please refer to the `LICENSE` file contained in the project root directory for more information.
 */

package io.adamantic.quicknote.amqp;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import io.adamantic.quicknote.Quicknote;
import io.adamantic.quicknote.QuicknoteConfig;
import io.adamantic.quicknote.Receiver;
import io.adamantic.quicknote.types.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration2.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Flow;
import java.util.stream.Collectors;

/**
 * Receives messages through AMQP protocol
 * @author Domenico Barra - domenico@adamantic.io
 */
@Slf4j
public class AmqpReceiver extends AmqpBaseChannel implements Receiver {

    AmqpReceiver(String name, AmqpConnector connector, QuicknoteConfig config) {
        super(name, connector, config);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    protected Configuration locateOwnConfig() {
        return config.configForReceiver(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void ownInitialize() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void subscribe(String routing, Flow.Subscriber<? super Message> subscriber) {

        DeliverCallback dcb = (consumerTag, message) -> {
            if (log.isTraceEnabled()) {
                log.trace("Received message: {}", message);
            }
            subscriber.onNext(buildMessage(message));
        };

        CancelCallback ccb = consumerTag -> {
            log.info("Subscription cancelled: {}", consumerTag);
            subscriber.onComplete();
        };

        try {
            switch(destType) {
                case "queue" -> subscribeToQueue(subscriber, dcb, ccb);
                case "topic" -> subscribeToTopic(routing, subscriber, dcb, ccb);
            }
        } catch (IOException e) {
            log.error("Error subscribing to {}", destName, e);
            subscriber.onError(e);
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void subscribe(Flow.Subscriber<? super Message> subscriber) {
        subscribe("#", subscriber);
    }

    private void subscribeToQueue(Flow.Subscriber<? super Message> subscriber,
                                  DeliverCallback dcb, CancelCallback ccb) throws IOException {
        var channel = getChannel();
        channel.queueDeclare(destName, durable, false, false, null);
        channel.basicConsume(destName, true, consumerTag(), dcb, ccb);
        log.info("Subscribed to queue {}", destName);
    }

    private void subscribeToTopic(String routing, Flow.Subscriber<? super Message> subscriber,
                                  DeliverCallback dcb, CancelCallback ccb) throws IOException {
        var channel = getChannel();
        channel.exchangeDeclare(destName, "topic", durable);
        var myQueue = channel.queueDeclare().getQueue();
        log.debug("Binding queue {} to exchange {} with routing key {}", myQueue, destName, routing);
        channel.queueBind(myQueue, destName, routing);
        channel.basicConsume(myQueue, true, consumerTag(), dcb, ccb);
        log.info("Subscribed to topic {} with routing key {}", destName, routing);
    }

    private static Message buildMessage(Delivery d) {
        var msgId = 0L;
        try {
            msgId = Long.parseLong(d.getProperties().getMessageId());
        } catch (NumberFormatException e) {
            msgId = Message.nextId();
            log.warn("Message ID is not a number: {} - assigning own system ID: {}",
                    d.getProperties().getMessageId(), msgId);

        }

        return new Message(msgId)
                .headers(d.getProperties().getHeaders() != null ?
                        d.getProperties().getHeaders().entrySet().stream()
                                .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        e -> e.getValue().toString()
                                )) : new HashMap<>())
                .contentType(d.getProperties().getContentType())
                .payload(d.getBody())
                .routing(d.getEnvelope().getRoutingKey());
    }

    private String consumerTag() {
        return "qn-java-cns-" + name + "-" + Quicknote.clientId();
    }
}
