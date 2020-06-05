package com.aha.tech.service;

import com.aha.tech.model.entity.MessageLogEntity;

/**
 * created by tangkun on 2019-05-23
 */
public interface MessageLogService {

    void add(MessageLogEntity messageLogEntity);


    /**
     * 消息消费完成
     *
     * @param uuid
     */
    void success(String uuid);

    void setMessageStatus(String uuid, int messageLogStatus);

    Integer selectStatusByUuid(String uuid);

    boolean recordMessageLog(String uuid, MessageLogEntity messageLogEntity);
}
