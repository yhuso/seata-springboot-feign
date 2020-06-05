package com.aha.tech.exception;

import com.aha.tech.commons.exception.BaseException;

/**
 * created by tangkun on 2019-05-22
 */
public class MessageException extends BaseException {

    public MessageException(String msg, int code) {
        super(msg, code);
    }

    public MessageException(String msg, int code, Throwable cause) {
        super(msg, code, cause);
    }

    public MessageException(int code, Throwable cause) {
        super(code, cause);
    }

    public MessageException(ExceptionCodeMessage exceptionCodeMessage) {
        super(exceptionCodeMessage.message, exceptionCodeMessage.code);
    }

    public MessageException(ExceptionCodeMessage exceptionCodeMessage,Throwable cause) {
        super(exceptionCodeMessage.message, exceptionCodeMessage.code,cause);
    }

    public MessageException(String msg, Throwable cause) {
        super(msg, ExceptionCodeMessage.DEFAULT_ERROR_CODE.code, cause);
    }

    public MessageException(String msg) {
        super(msg, ExceptionCodeMessage.DEFAULT_ERROR_CODE.code);
    }
}
