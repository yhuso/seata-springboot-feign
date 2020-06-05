package com.aha.tech.utils;

import org.apache.ibatis.session.RowBounds;

/**
 * 描述:
 *
 * @Auther: bianjie
 * @Date: 2020/5/15
 */
public class SQLUtils {
    /**
     * 全局共享，切勿修改
     * limit 0,1
     */
    public static final RowBounds LIMIT_ONE = new RowBounds(0, 1);
}
