//package com.aha.tech.event;
//
//import com.ctrip.framework.apollo.model.ConfigChangeEvent;
//import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.context.scope.refresh.RefreshScope;
//import org.springframework.stereotype.Component;
//
//import java.util.Set;
//
///**
// * @Author: luweihong
// * @Date: 2018/9/19
// *
// * 用于监听一些变量变更后的动作,如果无需后续动作可以不更新
// * apollo实时更新
// */
//@Component
//public class VariableRefreshEvent {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(VariableRefreshEvent.class);
//
//    @Autowired
//    private RefreshScope refreshScope;
//
//    /**
//     * apollo 配置变更时间
//     * @param changeEvent
//     */
//    @ApolloConfigChangeListener({"application"})
//    private void onChange(ConfigChangeEvent changeEvent) {
//        Set<String> changed = changeEvent.changedKeys();
//        changed.stream().forEach(s -> {
//            LOGGER.info("before refresh {}", changeEvent.getChange(s).getOldValue());
//            LOGGER.info("after refresh {}", changeEvent.getChange(s).getNewValue());
//        });
//    }
//}
