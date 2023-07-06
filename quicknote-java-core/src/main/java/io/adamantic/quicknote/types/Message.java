package io.adamantic.quicknote.types;

import io.adamantic.quicknote.exceptions.TimeToLiveExpired;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collections;
import java.util.Map;


@Data
@AllArgsConstructor
public class Message {

    private long id;
    private byte[] payload;
    private Map<String, String> headers;
    private String routing;
    private int ttl;

    public static final String              DEFAULT_ROUTING = "/";
    public static final Map<String, String> DEFAULT_HEADERS = Collections.emptyMap();
    public static final int                 DEFAULT_TTL = 16;

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
}
