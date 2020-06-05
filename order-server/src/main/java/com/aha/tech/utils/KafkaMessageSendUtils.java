package com.aha.tech.utils;

import com.aha.tech.base.exception.BusineException;
import com.aha.tech.message.consumer.base.dto.MessagePropertyModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 说明:发送消息生产者
 *
 * @author huangkeqi date:2016年9月1日
 */
public class KafkaMessageSendUtils {
    private static Logger logger = LogManager.getLogger(KafkaMessageSendUtils.class);

    @SuppressWarnings("unchecked")
    private static KafkaTemplate<String, Object> kafkaTemplate = SpringContextUtil.getBean(KafkaTemplate.class);

    /**
     * 说明:业务消息发送
     *
     * @throws BusineException date:2017年5月8日
     */
    public static void sendMsg(MessagePropertyModel<?> messagePropertyModel) throws BusineException {
        try {
            String sendMsg = JacksonUtil.objToJson(messagePropertyModel);
            // 生成key规则:topic+系统标示+targetType+单号id
            // 制定key,同一个单子会分发到同一个partitioner上
            if (logger.isDebugEnabled()) {
                logger.debug("[消息发送]sendMsg:{}", sendMsg);
            }
            send(messagePropertyModel.getTopic(), sendMsg);
        } catch (BusineException e) {
            throw e;
        } catch (Throwable ex) {
            logger.error("[消息发送]发送数据失败sendMessage:{}", messagePropertyModel, ex);
            throw new BusineException("[消息发送]回调响应出现异常uuid:" + messagePropertyModel.getUuid());
        }
    }


    /**
     * 发送
     *
     * @param topic
     * @param sendMsg
     */
    public static void send(String topic, String sendMsg) {
        ListenableFuture<SendResult<String, Object>> send = kafkaTemplate.send(topic, sendMsg);
        AtomicBoolean isOK = new AtomicBoolean(true);
        // 回调监听
        send.addCallback(result -> {
            // 成功
        }, ex -> {
            isOK.set(false);
            // 异常
            logger.error("[消息发送]发送数据失败sendMessage:{}", sendMsg, ex);
        });
        if (!isOK.get()) {
            throw new BusineException("[消息发送]FailureCallback异常:" + sendMsg);
        }
    }


    /**
     * 说明:新版高速发送OrderEvent数据
     *
     * @param msgList
     * @return date:2017年9月19日
     */
    public static <T> List<MessagePropertyModel<T>> sendMsgBatch(List<MessagePropertyModel<T>> msgList) {
        List<MessagePropertyModel<T>> sendFailList = new ArrayList<>();
        for (MessagePropertyModel<T> data : msgList) {
            String sendMsg = JacksonUtil.objToJson(data);
            try {
                // 生成key规则:topic+系统标示+targetType+单号id
                // 制定key,同一个单子会分发到同一个partitioner上
                if (logger.isDebugEnabled()) {
                    logger.debug("[消息发送]sendMsg:{}", sendMsg);
                }
                ListenableFuture<SendResult<String, Object>> send = kafkaTemplate.send(data.getTopic(), sendMsg);
                // 回调监听
                send.addCallback((SendResult<String, Object> result) -> {
                    // 成功
                }, (Throwable ex) -> {
                    // 异常
                    sendFailList.add(data);
                    logger.error("[消息发送]发送数据失败sendMessage:{}", sendMsg, ex);
                });
            } catch (Exception e) {
                logger.error("[消息发送]发送数据失败sendMsg:{},error:{}", sendMsg, e.getMessage(), e);
                sendFailList.add(data);
            }
        }
        return sendFailList;
    }


}
