package com.aha.tech.message.consumer.base.dto;

import lombok.Data;

/**
 * 描述:
 *
 * @Auther: bianjie
 * @Date: 2019-09-08
 */
@Data
public class MessagePropertyModel<T> {
    /**
     * 唯一uuid,用来处理重复消费检测
     */
    private String uuid;
    /**
     * 事件
     */
    private String event;
    private String time;

    private T properties;

    private Long userId;

    private String topic;

    private Long eventId;

    private int targetType;
    private int eventType;


}
