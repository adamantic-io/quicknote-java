/*
 * Copyright (c) 2023 by Adamantic S.r.l.
 * This file is part of a software library licensed under the GNU Lesser General Public License (LGPL) version 3.
 * Please refer to the `LICENSE` file contained in the project root directory for more information.
 */

package io.adamantic.quicknote;


import io.adamantic.quicknote.types.Message;

import java.io.IOException;
import java.util.concurrent.Flow;

/**
 * Abstracts an incoming channel.
 * Using the official reactive streams API implementation from
 * the Java SDK.
 * TODO: implement
 */
public interface Receiver extends Channel, Flow.Publisher<Message> {

    /**
     * Creates a new subscription for incoming messages over
     * a specific routing key (i.e. not "all messages", just a part).
     * @param routing the routing key to listen for
     * @param subscriber the message subscriber
     */
    void subscribe(String routing, Flow.Subscriber<? super Message> subscriber) throws IOException;

}
