/*
 * Copyright (c) 2023 by Adamantic S.r.l.
 * This file is part of a software library licensed under the GNU Lesser General Public License (LGPL) version 3.
 * Please refer to the `LICENSE` file contained in the project root directory for more information.
 */

package io.adamantic.quicknote.amqp.utils;

import io.adamantic.quicknote.types.Message;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Flow;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DummySubscriber implements Flow.Subscriber<Message> {

    public static final long      DEFAULT_TIMEOUT = 10;
    public static final TimeUnit  DEFAULT_TIMEUNIT = TimeUnit.SECONDS;
    private final LinkedBlockingQueue<Message> messages = new LinkedBlockingQueue<>();

    public Message waitForMessage() throws InterruptedException {
        return waitForMessage(DEFAULT_TIMEOUT, DEFAULT_TIMEUNIT);
    }

    public Message waitForMessage(long timeout, TimeUnit unit) throws InterruptedException {
        return messages.poll(timeout, unit);
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        log.debug("onSubscribe - {}", subscription);
    }

    @Override
    public void onNext(Message item) {
        log.debug("onNext - {}", item);
        try {
            messages.put(item);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("onError", throwable);
    }

    @Override
    public void onComplete() {
        log.debug("onComplete");
    }
}
