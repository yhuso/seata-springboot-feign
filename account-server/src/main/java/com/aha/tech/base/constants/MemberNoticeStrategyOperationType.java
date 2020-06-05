package com.aha.tech.base.constants;

/**
 * @author: ahaschool
 * @date: 2019-07-04 10:26
 */

public class MemberNoticeStrategyOperationType {
    /**
     * 策略执行类型 1 定时执行每天一次 2 每10分钟执行一次
     */
    public static final int STRATEGY_OPERATION_TYPE_1 = 1;
    public static final int STRATEGY_OPERATION_TYPE_2 = 2;

    /**
     * cron 表达式
     */
    public static final String STRATEGY_OPERATION_TYPE_1_CRON_FORMATTER = "%s %s %s * * ?";

    public static final String STRATEGY_OPERATION_TYPE_2_CRON_FORMATTER = "20 0/10 7-23 * * ? ";


}
