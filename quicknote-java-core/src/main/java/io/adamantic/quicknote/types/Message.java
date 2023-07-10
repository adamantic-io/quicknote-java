/*
 * Copyright (c) 2023 by Adamantic S.r.l.
 * This file is part of a software library licensed under the GNU Lesser General Public License (LGPL) version 3.
 * Please refer to the `LICENSE` file contained in the project root directory for more information.
 */

package io.adamantic.quicknote.types;

import io.adamantic.quicknote.exceptions.TimeToLiveExpired;
import lombok.*;

import java.util.Collections;
import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    public static final String              DEFAULT_ROUTING = "/";
    public static final String              DEFAULT_CONTENT_TYPE = "text/plain";
    public static final Map<String, String> DEFAULT_HEADERS = Collections.emptyMap();
    public static final byte[]              DEFAULT_PAYLOAD = new byte[0];
    public static final int                 DEFAULT_TTL = 16;


    /**
     * A very basic message id generator.
     * <p>
     * <strong>
     *     IMPORTANT: reloading this class will restart the numbering (there is no persistence).
     * </strong>
     * This is a very basic and - we should say - naive implementation, mostly
     * for testing purposes and basic use cases. Please pass your own ID - generated
     * however you want, but make sure it is unique across your system if you want
     * to save it somewhere.
     * </p>
     * <p><strong>
     *     Replace with your own implementation for concrete use cases.
     * </strong></p>
     */
    public static class IDGenerator {
        private static long nextId = 1;
        public long nextId() {
            return nextId++;
        }
    }

    @Getter @Setter
    private static IDGenerator idGenerator = new IDGenerator();

    private long id = nextId();
    private String contentType = DEFAULT_CONTENT_TYPE;
    private byte[] payload = DEFAULT_PAYLOAD;
    private Map<String, String> headers = DEFAULT_HEADERS;
    private String routing = DEFAULT_ROUTING;
    private int ttl = DEFAULT_TTL;

    /**
     * Creates a new message with a specific id, rather than asking
     * the ID generator to generate a new one.
     * Especially useful for incominc messages, where the id is already
     * generated by the sender.
     * @param id the message id
     */
    public Message(long id) {
        this.id = id;
    }

    /**
     * Decrements the time to live of this message.
     * If the time to live reaches zero, a {@link TimeToLiveExpired}
     * exception is thrown (unchecked).
     * @return the updated time to live
     */
    public int touchTtl() {
        if (--ttl <= 0) throw new TimeToLiveExpired(this);
        return ttl;
    }

    /**
     * Delegates message id generation to the installed id generator.
     * @return the next message id
     */
    public static long nextId() {
        return idGenerator.nextId();
    }

}
