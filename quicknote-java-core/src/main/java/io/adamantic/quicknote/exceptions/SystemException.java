package io.adamantic.quicknote.exceptions;

import lombok.Getter;

public class SystemException extends RuntimeException {

    @Getter
    private final ExceptionCode code;

    public SystemException(ExceptionCode code) {
        this.code = code;
    }

    public SystemException(ExceptionCode code, String message) {
        super(message);
        this.code = code;
    }

    public SystemException(ExceptionCode code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}
