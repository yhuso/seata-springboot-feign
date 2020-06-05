package com.aha.tech.exception;

import com.aha.tech.commons.exception.BaseException;

public class BadRequestParameterException extends BaseException {


    public BadRequestParameterException(String msg, int code) {
        super(msg, code);
    }

    public BadRequestParameterException(String msg, int code, Throwable cause) {
        super(msg, code, cause);
    }
}
