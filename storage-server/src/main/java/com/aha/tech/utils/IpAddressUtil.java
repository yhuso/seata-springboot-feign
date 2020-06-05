package com.aha.tech.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class IpAddressUtil {
    /**
     * 获取Ip地址
     *
     * @return
     */
    public static String getIp() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return "";
        }

        HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();
        String XFor = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(XFor)) {
            return XFor;
        }
        return "";
    }
}