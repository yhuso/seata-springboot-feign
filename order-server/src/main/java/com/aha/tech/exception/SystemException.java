package com.aha.tech.exception;

import com.aha.tech.commons.exception.BaseException;

/**
 * 说明:系统严重异常error级别
 * 
 * @author huangkeqi date:2016年12月8日
 */
public class SystemException extends BaseException {

    /****/
    private static final long serialVersionUID = -8866972497902845191L;

    public SystemException(String msg) {
        super(msg, ExceptionCodeMessage.DEFAULT_ERROR_CODE.code);
    }

    public SystemException(String msg, int code) {
        super(msg, code);
    }

    public SystemException(String msg, int code, Throwable cause) {
        super(msg, code, cause);
    }

    public SystemException(int code, Throwable cause) {
        super(code, cause);
    }

    public SystemException(String msg, Exception cause) {
        super(msg, ExceptionCodeMessage.DEFAULT_ERROR_CODE.code, cause);
    }
}
