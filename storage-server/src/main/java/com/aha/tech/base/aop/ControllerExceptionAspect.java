package com.aha.tech.base.aop;

import com.aha.tech.base.exception.MemberExceptionCode;
import com.aha.tech.commons.exception.BaseException;
import com.aha.tech.commons.response.RpcResponse;
import com.aha.tech.commons.symbol.Separator;
import com.aha.tech.exception.ExceptionCodeMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.util.Assert;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 接收controller层未捕获的异常
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class ControllerExceptionAspect {
    private static final String ASSERT_CLASS_NAME = Assert.class.getName();

    @ExceptionHandler(IllegalArgumentException.class)
    public RpcResponse<?> methodArgumentNotValidException(HttpServletRequest req, IllegalArgumentException ex) {
        StackTraceElement[] stackTrace = ex.getStackTrace();
        if (stackTrace.length > 0) {
            String className = stackTrace[0].getClassName();
            if (Objects.equals(className, ASSERT_CLASS_NAME)) {
                String message = ex.getMessage();
                StringBuilder stringBuilder = getLog(ex, req);
                log.warn("数据错误：{}", stringBuilder.toString());
                return new RpcResponse<>(ExceptionCodeMessage.ARGUMENT_NULL_ERROR.getCode(), message);
            }

        }
        return handle(ex, req);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, MissingServletRequestParameterException.class})
    public RpcResponse<?> methodArgumentNotValidException(HttpServletRequest req, Exception ex) {
        String message;
        if (ex instanceof MethodArgumentNotValidException) {

            message = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining(","));
        } else if (ex instanceof MissingServletRequestParameterException) {
            String parameterName = ((MissingServletRequestParameterException) ex).getParameterName();
            message = String.format("%s不能为空！", parameterName);
        } else {
            message = ex.getMessage();
        }
        StringBuilder stringBuilder = getLog(ex, req);
        log.warn("入参校验失败：{}", stringBuilder.toString());
        return new RpcResponse<>(ExceptionCodeMessage.ARGUMENT_NULL_ERROR.getCode(), message);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RpcResponse<?> handle(Exception ex, ServletRequest request) {
        StringBuilder stringBuilder = getLog(ex, request);
        if (ex instanceof BaseException) {
            log.warn("{}", stringBuilder.toString());
        } else {
            log.error("{}", stringBuilder.toString(), ex);
        }
        return new RpcResponse<>(MemberExceptionCode.ERROR_CODE_4200, ex.getMessage());
    }

    private StringBuilder getLog(Exception ex, ServletRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("错误信息 : ");
        sb.append(ex.getMessage());
        sb.append(System.lineSeparator());
        if (request instanceof ContentCachingRequestWrapper) {
            ContentCachingRequestWrapper wrapper = (ContentCachingRequestWrapper) request;
            String uri = wrapper.getRequestURI();
            sb.append("请求行 : ").append(uri);
            String queryString = wrapper.getQueryString();
            if (StringUtils.isNotBlank(queryString)) {
                sb.append("?").append(queryString);
            }
            sb.append(System.lineSeparator());

            String body = StringUtils.toEncodedString(wrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
            sb.append("请求体 : ").append(body);
        }
        return sb;

    }

}
