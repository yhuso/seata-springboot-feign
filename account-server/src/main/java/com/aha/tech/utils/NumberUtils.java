package com.aha.tech.utils;

/**
 * @author zhangjin
 * @date 2018-11-28
 */
public class NumberUtils {

    /**
     * 去掉小数点后面的0
     */
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }
}
