/*
 * Copyright (c) 2023 by Adamantic S.r.l.
 * This file is part of a software library licensed under the GNU Lesser General Public License (LGPL) version 3.
 * Please refer to the `LICENSE` file contained in the project root directory for more information.
 */

package io.adamantic.quicknote.amqp;

import io.adamantic.quicknote.Quicknote;
import io.adamantic.quicknote.amqp.utils.DummySubscriber;
import io.adamantic.quicknote.exceptions.ChannelNotFound;
import io.adamantic.quicknote.types.Message;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class SenderReceiverTest {


    /**
     * This test demonstrates how to send notification messages through Quicknote
     * in a hierarchical way. Use the routing system to send messages for a specific
     * purpose. The wildcard will match all sub-routings.
     *
     * Note: of course you will only need either the sending or the receiving part in your
     * code, just choose who you are.
     */
    @Test
    void exampleHierarchicalNotificationTest() throws ChannelNotFound, IOException, InterruptedException {

        /*
         * Optional (but recommended) set a client ID to identify your application with the message brokers.
         * This ensures consistency in the delivery mechanisms.
         */
        Quicknote.clientId("my-wonderful-app-backend");

        /*
         * Instantiate the Quicknote library - have a look at quicknote.yml for the configuration.
         * Then create the sender and the receiver. You can (and should) keep senders and receivers
         * as long as you need notifications flowing through the selected channels.
         * So do not put them in a "try-with-resources" if you are going to use them later.
         */
        var quicknote = Quicknote.instance();
        try (
                var sender = quicknote.sender("testsender");
                var receiver = quicknote.receiver("testreceiver"))
        {
            /*
             * This just acts as a local message box. Ideally, the receiving part of your application
             * should implement the `Flow.Subscriber<Message>` interface and handle the messages as they come.
             *
             * Here we are subscribing for whatever comes through the "notifications.user10425" routing key
             * AND all sub-routings. So we will receive messages from "notifications.user10425.auctions" and
             * "notifications.user10425.sales" as well.
             */
            var subscriber = new DummySubscriber();
            receiver.subscribe("notifications.user10425.*", subscriber);

            {
                // Sending a message in the ".auctions" sub-routing
                var msg = new Message()
                        .payload("Hello, world!".getBytes())
                        .routing("notifications.user10425.auctions");
                sender.send(msg);

                // Waiting for the message to arrive
                var rcm = subscriber.waitForMessage();
                assertEquals(msg, rcm);
            }

            {
                // Sending a message in the ".sales" sub-routing
                var msg = new Message()
                        .payload("Hello, world!".getBytes())
                        .routing("notifications.user10425.sales");
                sender.send(msg);

                // Waiting for the message to arrive
                var rcm = subscriber.waitForMessage();
                assertEquals(msg, rcm);
            }
        }
    }

    @Test
    void simpleSend() throws ChannelNotFound, IOException {
        var quicknote = Quicknote.instance();
        try (var sender = quicknote.sender("queue_quicknote")) {
            var msg = new Message()
                    .payload("Hello, world!".getBytes())
                    .routing("quicknote");

            sender.send(msg);
        }
    }

    @Test
    void sendAndReceive() throws ChannelNotFound, IOException, InterruptedException {
        var quicknote = Quicknote.instance();
        try (
                var sender = quicknote.sender("testsender");
                var receiver = quicknote.receiver("testreceiver"))
        {
            var subscriber = new DummySubscriber();
            receiver.subscribe(subscriber);
            var msg = new Message()
                    .payload("Hello, world!".getBytes());

            sender.send(msg);

            var rcm = subscriber.waitForMessage();
            assertEquals(msg, rcm);
        }
    }

    @Test
    void sendAndReceiveWithWrongRouting() throws ChannelNotFound, IOException, InterruptedException {

        var quicknote = Quicknote.instance();
        try (
                var sender = quicknote.sender("testsender");
                var receiver = quicknote.receiver("testreceiver"))
        {
            var subscriber = new DummySubscriber();
            receiver.subscribe("wrong.routing", subscriber);
            var msg = new Message()
                    .payload("Hello, world!".getBytes())
                    .routing("good.routing");

            sender.send(msg);

            var rcm = subscriber.waitForMessage(5, TimeUnit.SECONDS);
            assertNull(rcm);
        }
    }

    @Test
    void sendAndReceiveWithPartialRouting() throws ChannelNotFound, IOException, InterruptedException {
        var quicknote = Quicknote.instance();
        try (
                var sender = quicknote.sender("testsender");
                var receiver = quicknote.receiver("testreceiver"))
        {
            var subscriber = new DummySubscriber();
            receiver.subscribe("routing.*", subscriber);

            var msg = new Message()
                    .payload("Hello, world!".getBytes())
                    .routing("routing.323");

            sender.send(msg);

            var rcm = subscriber.waitForMessage();
            assertEquals(msg, rcm);
        }

    }

}
