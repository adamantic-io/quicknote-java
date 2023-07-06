package io.adamantic.quicknote.exceptions;

import lombok.Getter;

public class NotImplemented extends SystemException {

    @Getter
    String name;

    public NotImplemented(String name) {
        super(ExceptionCode.NOT_IMPLEMENTED);
        this.name = name;
    }
}
