package com.aha.tech.task.aop;

import com.aha.tech.utils.RedisLock;

import org.aspectj.lang.ProceedingJoinPoint;

import lombok.extern.slf4j.Slf4j;

/**
 * 描述:
 *
 * @Auther: bianjie
 * @Date: 2019-10-09
 */
@Slf4j
public class BaseLockAndLogAop {

    Object lockAndRun(ProceedingJoinPoint joinPoint, String methodName, String redisKey, String taskName) throws Throwable {
        RedisLock redisLock = new RedisLock(redisKey);
        boolean lockFlag = redisLock.lock(1, 0);
        if (!lockFlag) {
            log.warn("已经有其他服务正在运行此任务:{}！", methodName);
            return null;
        }

        try {
            boolean flag = false;
            long start = System.currentTimeMillis();
            Object value = null;
            try {
                value = joinPoint.proceed();
                flag = true;
            } catch (Exception e) {
                log.error("【{}】执行失败.", taskName, e);
            } finally {
                log.info("【{}】执行结果:{},耗时:{}ms.", taskName, flag, (System.currentTimeMillis() - start));
            }
            return value;
        } finally {
            redisLock.unlock();
        }
    }
}
