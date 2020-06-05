//package com.aha.tech.message.consumer.base;
//
//import com.aha.tech.exception.MessageAlreadyConsumerException;
//import com.aha.tech.memberserver.facade.constants.MessageLogStatus;
//import com.aha.tech.message.consumer.base.dto.MessagePropertyModel;
//import com.aha.tech.model.entity.MessageLogEntity;
//import com.aha.tech.service.MessageLogService;
//import com.aha.tech.utils.JacksonUtil;
//import com.fasterxml.jackson.databind.JavaType;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.common.base.CaseFormat;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.ResolvableType;
//import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;
//import java.util.List;
//
//@Slf4j
//@Component
//public abstract class BasePropertyConsumer<T> {
//
//    @Autowired
//    private MessageLogService messageLogService;
//    @Autowired
//    private ThreadPoolTaskExecutor coreThreadPool;
//
//    private JavaType javaType;
//
//    protected void doAround(List<ConsumerRecord<String, String>> records, String listenerName) {
//        doAround(records, listenerName, false);
//    }
//
//    protected void doAround(List<ConsumerRecord<String, String>> records, String listenerName, boolean useThreadPool) {
//        records.forEach(message -> {
//            try {
//                if (useThreadPool) {
//                    // 另外使用线程消费
//                    coreThreadPool.submit(() -> doAround(message, listenerName));
//                } else {
//                    doAround(message, listenerName);
//                }
//            } catch (Exception e) {
//                log.warn("kafka消费异常。", e);
//            }
//        });
//    }
//
//    protected void doAround(ConsumerRecord<String, String> consumerRecord, String listenerName) {
//        String topic = consumerRecord.topic();
//        String message = consumerRecord.value();
//
//        log.info("{}收到消息,topic={},message:{}", listenerName, topic, message);
//
//        // 获取数据传输model
//        MessagePropertyModel<T> msgModel = getTargetJsonObj(message);
//
//        // 数据正确性检查
//        if (msgModel == null || StringUtils.isBlank(msgModel.getUuid())) {
//            return;
//        }
//        if (!isSupport(msgModel)) {
//            log.info("uuid:{}消息无需处理。", msgModel.getUuid());
//            return;
//        }
//
//        // 插入消息日志记录
//        MessageLogEntity messageLogEntity = createMessageLog(message, msgModel.getUuid(), topic);
//
//        String uuid = msgModel.getUuid();
//        //是否重复消费
//        boolean dupleConsume = messageLogService.recordMessageLog(uuid, messageLogEntity);
//        if (dupleConsume) {
//            return;
//        }
//
//        // 实际处理 记录事务
//        try {
//            // 调用具体处理消费者
//            this.doProcess(msgModel);
//            //消息状态修改
//            messageLogService.success(uuid);
//        } catch (MessageAlreadyConsumerException e) {
//            log.warn(e.getMessage());
//            //消息状态修改
//            messageLogService.success(uuid);
//        } catch (Throwable e) {
//            log.error("消费处理异常,message:{}", message, e);
//            messageLogService.setMessageStatus(uuid, MessageLogStatus.FAIL);
//        }
//    }
//
//    /**
//     * 具体消费方法
//     *
//     * @param messageModel
//     */
//    protected abstract void doProcess(MessagePropertyModel<T> messageModel);
//
//    /**
//     * 判断是否支持消费
//     *
//     * @param messageModel
//     * @return
//     */
//    protected boolean isSupport(MessagePropertyModel<T> messageModel) {
//        return true;
//    }
//
//    private MessageLogEntity createMessageLog(String message, String uuid, String topic) {
//        MessageLogEntity messageLogEntity = new MessageLogEntity();
//        messageLogEntity.setUuid(uuid);
//        messageLogEntity.setBody(message);
//        messageLogEntity.setTopic(topic);
//        String simpleName = this.getClass().getSimpleName();
//        String beanName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, simpleName);
//        messageLogEntity.setProcessBean(beanName);
//        messageLogEntity.setProcessMethod("doProcess");
//        //设置为处理中 避免补偿扫描到
//        messageLogEntity.setStatus(MessageLogStatus.PROCESSING);
//        return messageLogEntity;
//    }
//
//
//    protected MessagePropertyModel<T> getTargetJsonObj(String message) {
//        JavaType javaType = getJavaType();
//        ObjectMapper mapper = JacksonUtil.getMapper();
//        try {
//            return mapper.readValue(message, javaType);
//        } catch (IOException e) {
//            throw new IllegalArgumentException("json格式化MessageModel错误。");
//        }
//    }
//
//    @SuppressWarnings("unchecked")
//    private JavaType getJavaType() {
//        if (javaType != null) {
//            return javaType;
//        }
//
//        Class<T> clazz = (Class<T>) ResolvableType.forClass(this.getClass()).getSuperType().getGeneric(0).resolve();
//        Type[] actualTypeArguments = new Type[1];
//        actualTypeArguments[0] = clazz;
//        ParameterizedTypeImpl parameterizedType = new ParameterizedTypeImpl(actualTypeArguments, null, MessagePropertyModel.class);
//        ObjectMapper mapper = JacksonUtil.getMapper();
//        javaType = mapper.constructType(parameterizedType);
//        return javaType;
//    }
//
//
//    static class ParameterizedTypeImpl implements ParameterizedType {
//        private final Type[] actualTypeArguments;
//        private final Type ownerType;
//        private final Type rawType;
//
//        public ParameterizedTypeImpl(Type[] actualTypeArguments, Type ownerType, Type rawType) {
//            this.actualTypeArguments = actualTypeArguments;
//            this.ownerType = ownerType;
//            this.rawType = rawType;
//        }
//
//        @Override
//        public Type[] getActualTypeArguments() {
//            return this.actualTypeArguments;
//        }
//
//        @Override
//        public Type getOwnerType() {
//            return this.ownerType;
//        }
//
//        @Override
//        public Type getRawType() {
//            return this.rawType;
//        }
//    }
//}
