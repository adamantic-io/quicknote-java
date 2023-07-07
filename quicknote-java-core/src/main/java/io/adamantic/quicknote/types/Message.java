package io.adamantic.quicknote.types;

import io.adamantic.quicknote.exceptions.TimeToLiveExpired;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

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


    private static long nextId = 1;

    private long id = nextId();
    private String contentType = DEFAULT_CONTENT_TYPE;
    private byte[] payload = DEFAULT_PAYLOAD;
    private Map<String, String> headers = DEFAULT_HEADERS;
    private String routing = DEFAULT_ROUTING;
    private int ttl = DEFAULT_TTL;

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
     * Returns the next message id.
     * <p>
     * <strong>
     *     IMPORTANT: reloading this class will restart the numbering (there is no persistence).
     * </strong>
     * This is a very basic and - we should say - naive implementation, mostly
     * for testing purposes and basic use cases. Please pass your own ID - generated
     * however you want, but make sure it is unique across your system if you want
     * to save it somewhere.
     * </p>
     * @return the next message id
     */
    public static long nextId() {
        return nextId++;
    }
}
