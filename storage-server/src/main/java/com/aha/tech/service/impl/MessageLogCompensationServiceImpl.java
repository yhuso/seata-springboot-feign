//package com.aha.tech.service.impl;
//
//import com.aha.tech.exception.MessageAlreadyConsumerException;
//import com.aha.tech.memberserver.facade.constants.MessageLogStatus;
//import com.aha.tech.model.entity.MessageLogCompensationEntity;
//import com.aha.tech.model.entity.MessageLogEntity;
//import com.aha.tech.model.mapper.MessageLogCompensationMapper;
//import com.aha.tech.service.MessageLogCompensationService;
//import com.aha.tech.utils.SpringContextUtil;
//
//import org.apache.commons.collections4.CollectionUtils;
//import org.apache.commons.lang3.reflect.MethodUtils;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.support.TransactionTemplate;
//
//import java.lang.reflect.InvocationTargetException;
//import java.util.List;
//import java.util.Objects;
//import java.util.Optional;
//import java.util.concurrent.TimeUnit;
//
//import lombok.extern.slf4j.Slf4j;
//
//import static com.aha.tech.utils.KeyGenerateUtil.LATEST_MESSAGE_LOG_ID_KEY;
//
///**
// * @author ahaschool
// * @date 2020-03-20 08:48
// * @since
// */
//@Service
//@Slf4j
//public class MessageLogCompensationServiceImpl implements MessageLogCompensationService {
//
//    private final StringRedisTemplate redisTemplate;
//    private final MessageLogCompensationMapper messageLogMapper;
//    private final TransactionTemplate transactionTemplate;
//
//    public MessageLogCompensationServiceImpl(StringRedisTemplate redisTemplate,
//                                             MessageLogCompensationMapper messageLogMapper,
//                                             TransactionTemplate transactionTemplate) {
//        this.redisTemplate = redisTemplate;
//        this.messageLogMapper = messageLogMapper;
//        this.transactionTemplate = transactionTemplate;
//    }
//
//    @Override
//    public void insert(MessageLogEntity messageLogEntity) {
//        if (messageLogEntity == null) {
//            return;
//        }
//        MessageLogCompensationEntity compensationEntity = MessageLogCompensationEntity.builder()
//                .uuid(messageLogEntity.getUuid())
//                .body(messageLogEntity.getBody())
//                .topic(messageLogEntity.getTopic())
//                .processBean(messageLogEntity.getProcessBean())
//                .processMethod(messageLogEntity.getProcessMethod())
//                //设置为处理中 避免补偿扫描到
//                .status(MessageLogStatus.PROCESS_NOT)
//                .build();
//        messageLogMapper.insertSelective(compensationEntity);
//
//    }
//
//    @Override
//    public void messageCompensation() {
//        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
//        String value = valueOperations.get(LATEST_MESSAGE_LOG_ID_KEY);
//        Long latestId = Optional.ofNullable(value).map(Long::parseLong).orElse(0L);
//        List<MessageLogCompensationEntity> messageLogEntities = messageLogMapper.selectCompensation(latestId);
//        if (CollectionUtils.isEmpty(messageLogEntities)) {
//            return;
//        }
//        boolean allSuccess = true;
//        for (MessageLogCompensationEntity messageLogEntity : messageLogEntities) {
//            log.info("消息补偿：{}.", messageLogEntity);
//            //如果已处理或者在处理中，则忽略
//            if (Objects.equals(messageLogEntity.getStatus(), MessageLogStatus.PROCESSED) ||
//                    Objects.equals(messageLogEntity.getStatus(), MessageLogStatus.PROCESSING)) {
//                log.warn("该消息已经消费成功或正在消费:{}，status:{}.", messageLogEntity, messageLogEntity.getStatus());
//                continue;
//            }
//            boolean flag = true;
//            try {
//                doCompensation(messageLogEntity);
//            } catch (Exception e) {
//                flag = false;
//                allSuccess = false;
//                log.error("消息补偿失败：{}.", messageLogEntity, e);
//            }
//            MessageLogCompensationEntity.MessageLogCompensationEntityBuilder logEntityBuilder = MessageLogCompensationEntity.builder().id(messageLogEntity.getId());
//            if (flag) {
//                logEntityBuilder.status(MessageLogStatus.PROCESSED);
//            } else {
//                Integer retryNum = messageLogEntity.getRetryNum();
//                logEntityBuilder.retryNum(retryNum - 1);
//                if (retryNum <= 0) {
//                    logEntityBuilder.status(MessageLogStatus.FAIL);
//                }
//            }
//            messageLogMapper.updateByPrimaryKeySelective(logEntityBuilder.build());
//        }
//        if (allSuccess) {
//            latestId = messageLogEntities.get(messageLogEntities.size() - 1).getId();
//            valueOperations.set(LATEST_MESSAGE_LOG_ID_KEY, String.valueOf(latestId), 2, TimeUnit.DAYS);
//        }
//
//
//    }
//
//    public void doCompensation(MessageLogCompensationEntity messageLogEntity) throws Exception {
//        String beanName = messageLogEntity.getProcessBean();
//        String methodName = messageLogEntity.getProcessMethod();
//        String body = messageLogEntity.getBody();
//        Object springBean = SpringContextUtil.getBean(beanName);
//        if (springBean == null) {
//            log.error("消息补偿时查找不到bean:{}.", beanName);
//            throw new IllegalArgumentException("不存在该spring bean");
//        }
//        // 获取执行对象 getMsgTargetJson
//
//        Object targetJsonObj = MethodUtils.invokeMethod(springBean, true, "getTargetJsonObj", body);
//        transactionTemplate.execute((status) -> {
//            try {
//                MethodUtils.invokeMethod(springBean, true, methodName, targetJsonObj);
//            } catch (InvocationTargetException e) {
//                if (!(e.getTargetException() instanceof MessageAlreadyConsumerException)) {
//                    // 非 MessageAlreadyConsumerException 异常 直接抛出
//                    throw new RuntimeException(e);
//                }
//            } catch (NoSuchMethodException | IllegalAccessException e) {
//                throw new RuntimeException(e);
//            }
//            return null;
//        });
//    }
//}
