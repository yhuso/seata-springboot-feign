package com.aha.tech.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 描述:
 *
 * @Auther: bianjie
 * @Date: 2019/11/19
 */
@Getter
@AllArgsConstructor
public enum ExternalTopicEventEnum {
    ORDER_CANCEL_ORDER("topic", 1, 100, "取消订单事件");

    private String eventFrom;
    private int targetType;
    private int eventType;
    private String desc;

}
