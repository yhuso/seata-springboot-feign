package com.aha.tech.base.aop;

import com.aha.tech.utils.KeyGenerateUtil;
import com.aha.tech.utils.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class RedisLockAop {


    @Around("@annotation(com.aha.tech.base.aop.RedisLock)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String rockKey = KeyGenerateUtil.redisLockKey(className, methodName);
        RedisLock redisLock = new RedisLock(rockKey);
        boolean lockFlag = redisLock.lock(5);
        if (!lockFlag) {
            log.error("获取分布式锁失败key:{}", methodName);
            throw new CannotAcquireLockException("服务异常获取分布式锁失败！");
        }
        try {
            return joinPoint.proceed();
        } finally {
            redisLock.unlock();
        }

    }
}
