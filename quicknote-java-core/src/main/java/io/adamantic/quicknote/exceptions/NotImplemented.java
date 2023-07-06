package io.adamantic.quicknote.exceptions;

import lombok.Getter;

public class NotImplemented extends SystemException {

    @Getter
    String name;

    public NotImplemented(String name) {
        super(ExceptionCode.FTR_NOTIMPL, "Not implemented");
        this.name = name;
    }
}
