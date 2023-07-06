package io.adamantic.quicknote.exceptions;

import io.adamantic.quicknote.types.Message;
import lombok.Getter;

public class TimeToLiveExpired extends SystemException {

    @Getter
    Message expiredMessage;

    public TimeToLiveExpired(Message expiredMessage) {
        super(ExceptionCode.MSG_TTLEXP);
        this.expiredMessage = expiredMessage;
    }
}
