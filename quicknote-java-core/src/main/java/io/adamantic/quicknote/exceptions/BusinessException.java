package io.adamantic.quicknote.exceptions;

import lombok.Getter;

public class BusinessException extends Exception {

    @Getter
    private final ExceptionCode code;

    public BusinessException(ExceptionCode code) {
        this.code = code;
    }


    public BusinessException(ExceptionCode code, String message) {
        super(message);
        this.code = code;
    }


    public BusinessException(ExceptionCode code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

}
