package com.aha.tech.constant;

import com.aha.tech.utils.SpringContextUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

import javax.validation.constraints.Null;
import java.util.Arrays;

/**
 * 事件类型
 *
 * @author: ahaschool
 * @date: 2019-06-03 11:14
 */
@Getter
@AllArgsConstructor
public enum MessageEventEnum {

    /**
     * 1支付成功不允许 会员权益增加
     */
    NOT_ADD_MEMBER_PRIVILEGE(3, 1, 1, "${kafka.topic.cannotAddMemberPrivilege.producer}"),

    /**
     * 2 会员权益增加或更新
     */
    MEMBER_PRIVILEGE_CREATED_OR_UPDATED(3, 2, 2, "${kafka.topic.memberPrivilegeCreateOrUpdate.producer}"),

    WITHHOLD_OF_FIRST(3, 3, 3, "${kafka.topic.withholdOfFirst.producer}"),
    ;

    private int eventFrom;
    private int targetType;
    private int eventType;
    private String topicPropertyKey;


    public static String getTopic(int eventType) {
        ApplicationContext applicationContext = SpringContextUtil.getApplicationContext();
        AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();
        ConfigurableBeanFactory configurableBeanFactory = null;
        if (beanFactory instanceof ConfigurableBeanFactory) {
            configurableBeanFactory = (ConfigurableBeanFactory) beanFactory;

        }
        if (configurableBeanFactory == null) {
            throw new NoSuchBeanDefinitionException(ConfigurableBeanFactory.class);
        }

        return Arrays.stream(MessageEventEnum.values())
                .filter(eventEnum -> eventEnum.getEventType() == eventType)
                .findFirst()
                .map(MessageEventEnum::getTopicPropertyKey)
                .map(configurableBeanFactory::resolveEmbeddedValue)
                .orElseThrow(() -> new IllegalArgumentException("event type " + eventType + " invalid"));
    }


}
