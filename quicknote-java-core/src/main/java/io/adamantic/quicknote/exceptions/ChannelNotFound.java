package io.adamantic.quicknote.exceptions;

import lombok.Getter;

public class ChannelNotFound extends BusinessException {

    @Getter
    String name;

    public ChannelNotFound(String name) {
        super(ExceptionCode.CHANNEL_NOT_FOUND);
        this.name = name;
    }
}
