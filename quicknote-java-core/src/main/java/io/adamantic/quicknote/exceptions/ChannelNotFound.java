package io.adamantic.quicknote.exceptions;

import lombok.Getter;

public class ChannelNotFound extends BusinessException {

    @Getter
    String name;

    public ChannelNotFound(String name) {
        super(ExceptionCode.CHN_NOTFOUND, "Channel not found: " + name);
        this.name = name;
    }

    public ChannelNotFound(String name, Throwable cause) {
        super(ExceptionCode.CHN_NOTFOUND, "Channel not found: " + name, cause);
        this.name = name;
    }
}
