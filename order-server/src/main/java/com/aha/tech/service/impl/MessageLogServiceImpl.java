//package com.aha.tech.service.impl;
//
//import com.aha.tech.memberserver.facade.constants.MessageLogStatus;
//import com.aha.tech.model.entity.MessageLogEntity;
//import com.aha.tech.model.mapper.MessageLogMapper;
//import com.aha.tech.service.MessageLogCompensationService;
//import com.aha.tech.service.MessageLogService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import tk.mybatis.mapper.entity.Example;
//import tk.mybatis.mapper.util.Sqls;
//
//import javax.annotation.Resource;
//import java.util.Objects;
//
///**
// * created by tangkun on 2019-05-23
// */
//@Service
//@Slf4j
//public class MessageLogServiceImpl implements MessageLogService {
//
//    @Resource
//    private MessageLogMapper messageLogMapper;
//    @Autowired
//    private MessageLogCompensationService messageLogCompensationService;
//
//    @Override
//    public void add(MessageLogEntity messageLogEntity) {
//        messageLogMapper.insertSelective(messageLogEntity);
//    }
//
//    @Override
//    public void success(String uuid) {
//        setMessageStatus(uuid, MessageLogStatus.PROCESSED);
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void setMessageStatus(String uuid, int messageLogStatus) {
//        Example example = createByUUID(uuid);
//        MessageLogEntity messageLogEntity = new MessageLogEntity();
//        messageLogEntity.setStatus(messageLogStatus);
//        messageLogMapper.updateByExampleSelective(messageLogEntity, example);
//        if (Objects.equals(messageLogStatus, MessageLogStatus.FAIL)) {
//            // 消费失败 插入重试表
//            MessageLogEntity entity = messageLogMapper.selectOne(MessageLogEntity.builder().uuid(uuid).build());
//            messageLogCompensationService.insert(entity);
//        }
//    }
//
//    @Override
//    public Integer selectStatusByUuid(String uuid) {
//        Example example = Example.builder(MessageLogEntity.class)
//                .select("status")
//                .where(Sqls.custom().andEqualTo("uuid", uuid))
//                .build();
//        MessageLogEntity messageLogEntity = messageLogMapper.selectOneByExample(example);
//        if (messageLogEntity == null) {
//            return null;
//        }
//        return messageLogEntity.getStatus();
//    }
//
//
//    private Example createByUUID(String uuid) {
//        Example example = new Example(MessageLogEntity.class);
//        example.createCriteria().andEqualTo("uuid", uuid);
//        return example;
//    }
//
//    /**
//     * 检查，记录消息日志
//     *
//     * @return true 重复消费;false 不是重复消息
//     */
//    @Override
//    public boolean recordMessageLog(String uuid, MessageLogEntity messageLogEntity) {
//        Integer messageStatus = selectStatusByUuid(uuid);
//        if (messageStatus != null) {
//            log.warn("消息已被消费uuid:{}.", uuid);
//            return true;
//        }
//        add(messageLogEntity);
//        return false;
//    }
//
//}
