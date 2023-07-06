package io.adamantic.quicknote.exceptions;

import lombok.Getter;

public class ConfigException extends SystemException {


    public ConfigException(String message) {
        super(ExceptionCode.CFG_EXCEPTION, message);
    }

    public ConfigException(String message, Throwable cause) {
        super(ExceptionCode.CFG_EXCEPTION, message, cause);
    }

}
