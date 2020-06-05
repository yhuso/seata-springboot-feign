package com.aha.tech.base.exception;


import com.aha.tech.commons.exception.BaseException;

/**
 * 说明:业务处理异常warn级别
 *
 * @author huangkeqi date:2016年12月8日
 */
public class BusineException extends BaseException {


    private static final long serialVersionUID = -2423279393324395996L;

    public BusineException(String msg) {
        super(msg, MemberExceptionCode.ERROR_CODE_4200);
    }

    public BusineException(String msg, int code) {
        super(msg, code);
    }

    public BusineException(String msg, int code, Throwable cause) {
        super(msg, code, cause);
    }

    public BusineException(int code, Throwable cause) {
        super(code, cause);
    }

    public BusineException(String msg, Exception cause) {
        super(msg, MemberExceptionCode.ERROR_CODE_4200, cause);
    }



}
