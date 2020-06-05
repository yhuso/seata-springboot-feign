package com.aha.tech.utils;

/**
 * @Author: monkey
 * @Date: 2018/7/28
 * <p>
 * redis key 定义类
 */
public class KeyGenerateUtil {

    public final static String MEMBER_SERVER_REDIS_KEY_PREFIX = "member:server";
    /**
     * 处理过的最近的id key
     */
    public final static String LATEST_MSG_ID_KEY = "member:server:latest:msg:id";
    /**
     * 处理过的最近的id
     */
    public final static String LATEST_MESSAGE_LOG_ID_KEY = "member:server:latest:msg:log:id";
    /**
     * 处理过的event最近的id
     */
    public final static String LATEST_MESSAGE_EVENT_ID_KEY = "member:server:latest:msg:event:id";
    /**
     * 临期过期规则list key
     */
    public final static String MEMBER_SERVER_MEMBER_EXPIRE_RULE_KEY = "member:server:member:expire:rule:key";

    /**
     * 会员权益获得用户 Key
     *
     * @param strategyId 策略id
     */
    public static String noticeStrategyVipGetKey(long strategyId) {
        return String.format("member:server:notice:strategy:%s:vip:get", strategyId);
    }

    /**
     * 定时任务key
     *
     * @param className
     * @param methodName
     * @return
     */
    public static String taskRockKey(String className, String methodName) {
        return String.format("member:server:task:%s:%s", className, methodName);
    }

    /**
     * 分布式锁key
     *
     * @param className
     * @param methodName
     * @return
     */
    public static String redisLockKey(String className, String methodName) {
        return String.format("member:server:lock:%s:%s", className, methodName);
    }

    /**
     * 当多个方法需要分布式锁时，生成锁的key
     *
     * @param keyPrefix
     * @param key
     * @return
     */
    public static String methodLockKey(String keyPrefix, String key) {
        return String.format("member:server:lock:%s:%s", keyPrefix, key);
    }

    /**
     * member 对象
     *
     * @param userId
     * @return
     */
    public static String memberKey(long userId) {
        return String.format("member:server:member:%s", userId);
    }

    /**
     * member 创建时锁
     *
     * @param userId
     * @return
     */
    public static String memberCreateKey(long userId) {
        return String.format("member:server:member:%s:insert", userId);
    }

    /**
     * 订单支付成功和签约事件。不能同时进行
     *
     * @param userId
     * @return
     */
    public static String memberOrderContractKey(long userId) {
        return String.format("member:server:member:order:contract:%s", userId);
    }

    /**
     * member privilege level 对象
     *
     * @param userId
     * @return
     */
    public static String memberPrivilegeLevelKey(long userId) {
        return String.format("member:server:member:%s:privilege:level", userId);
    }

    /**
     * privilege level 对象
     *
     * @param id
     * @return
     */
    public static String privilegeLevelKey(Long id) {
        return String.format("member:server:privilege:level:%d", id);
    }

    /**
     * privilege type list 对象
     *
     * @param levelId
     * @return
     */
    public static String privilegeTypeKey(long levelId) {
        return String.format("member:server:privilege:type:%d", levelId);
    }

    /**
     * privilege type list 对象
     *
     * @param userId
     * @return
     */
    public static String cancelOrderUserKey(long userId) {
        return String.format("member:server:cancel:user:%d:order", userId);
    }
}
