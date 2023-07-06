package io.adamantic.quicknote.exceptions;

import io.adamantic.quicknote.types.Message;
import lombok.Getter;

public class TimeToLiveExpired extends SystemException {

    @Getter
    Message expiredMessage;

    public TimeToLiveExpired(Message expiredMessage) {
        super(ExceptionCode.TTL_EXPIRED);
        this.expiredMessage = expiredMessage;
    }
}
