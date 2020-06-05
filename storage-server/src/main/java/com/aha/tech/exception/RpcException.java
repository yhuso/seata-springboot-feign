package com.aha.tech.exception;

import com.aha.tech.commons.exception.BaseException;

public class RpcException extends BaseException {
    public RpcException(String msg, int code) {
        super(msg, code);
    }

    public RpcException(String msg, int code, Throwable cause) {
        super(msg, code, cause);
    }

    public RpcException(int code, Throwable cause) {
        super(code, cause);
    }
}
