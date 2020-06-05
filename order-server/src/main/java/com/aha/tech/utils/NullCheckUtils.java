package com.aha.tech.utils;

/**
 * @author tangkun
 * @date 2019-07-10
 */
public class NullCheckUtils {

    public static boolean isNotNull(Integer integer) {
        return integer != null && integer > 0;
    }

    public static boolean isNull(Integer integer) {
        return !isNotNull(integer);
    }
}
