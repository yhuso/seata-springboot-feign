package com.aha.tech.exception;

import com.aha.tech.commons.exception.BaseException;

public class ServiceException extends BaseException {
    public ServiceException(String msg, int code) {
        super(msg, code);
    }

    public ServiceException(String msg, int code, Throwable cause) {
        super(msg, code, cause);
    }

    public ServiceException(ExceptionCodeMessage exceptionCodeMessage) {
        super(exceptionCodeMessage.message,exceptionCodeMessage.code);
    }
}
