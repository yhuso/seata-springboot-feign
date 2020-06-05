package com.aha.tech.task.scheduling.config;

import com.aha.tech.MemberApplicationTests;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author: ahaschool
 * @date: 2019-07-04 17:26
 */

public class TriggerType3TaskTest extends MemberApplicationTests {
    @Autowired
    private MemberPrivilegeLevelService memberPrivilegeLevelService;
    @Autowired
    private MemberMsgPushTimeLogService memberMsgPushTimeLogService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private MemberNoticeStrategyService memberNoticeStrategyService;

    @Test
    public void run() {
        TriggerType3Task triggerType3Task = new TriggerType3Task(68, memberNoticeStrategyService, memberPrivilegeLevelService, memberMsgPushTimeLogService, stringRedisTemplate);
        triggerType3Task.run();

    }

}
