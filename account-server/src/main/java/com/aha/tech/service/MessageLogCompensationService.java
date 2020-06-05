package com.aha.tech.service;

import com.aha.tech.model.entity.MessageLogEntity;

/**
 * @author ahaschool
 * @date 2020-03-20 08:48
 * @since
 */

public interface MessageLogCompensationService {

    /**
     * 插入需要补偿的消息
     *
     * @param messageLogEntity
     */
    void insert(MessageLogEntity messageLogEntity);

    /**
     * 消息补偿
     */
    void messageCompensation();


}
