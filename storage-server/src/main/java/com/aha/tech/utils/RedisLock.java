package com.aha.tech.utils;

import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @Author: luweihong
 * @Date: 2018/5/2
 */
public class RedisLock {

    public static final Logger logger = LoggerFactory.getLogger(RedisLock.class);

    private final static String LOCK_PREFIX = "redis:lock:";

    private String lockKey;

    private final static Long EXPIRE_TIME = 50 * 1000L;

    private final static Long WAIT_TIME = 10 * 1000L;

    private final RedissonUtil redissonUtil = SpringContextUtil.getBean(RedissonUtil.class);

    public RedisLock(String lockKey) {
        this.lockKey = LOCK_PREFIX + lockKey;
    }

    public boolean lock() {
        return this.lock(1);
    }

    /**
     * 获得锁
     */
    public boolean lock(int retryNum) {
        return lock(retryNum, WAIT_TIME);
    }

    /**
     * 获得锁
     */
    public boolean lock(int retryNum, long waitTime) {
        return lock(retryNum, EXPIRE_TIME, waitTime);
    }

    public boolean lock(int retryNum, long expireTime, long waitTime) {
        boolean flag = false;
        logger.info("key:{}开始获取分布式，currentRetryNum:{}.", lockKey, retryNum);
        try {
            while (retryNum > 0) {
                RLock rLock = redissonUtil.getRLock(lockKey);
                // 尝试加锁，最多等待wait time 毫秒，上锁以后 EXPIRE_TIME 毫秒秒自动解锁
                boolean isLock = rLock.tryLock(waitTime, expireTime, TimeUnit.MILLISECONDS);
                logger.info("key:{}获取分布式锁状态:{},currentRetryNum:{}.", lockKey, isLock, retryNum);
                if (isLock) {
                    flag = true;
                    break;
                }
                retryNum--;
            }
        } catch (Exception e) {
            throw new RuntimeException("服务异常获取分布式锁失败！", e);
        } finally {
            logger.info("key:{}获取分布式锁结果状态:{},currentRetryNum:{}.", lockKey, flag, retryNum);
        }

        return flag;
    }

    /**
     * 解锁
     */
    public void unlock() {
        RLock rLock = redissonUtil.getRLock(lockKey);
        if (rLock.isHeldByCurrentThread()) {
            rLock.unlock();
        }
    }

}
