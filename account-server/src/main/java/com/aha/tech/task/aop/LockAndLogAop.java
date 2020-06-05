package com.aha.tech.task.aop;

import com.aha.tech.utils.KeyGenerateUtil;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;


@Order(0)
@Component
@Aspect
public class LockAndLogAop extends BaseLockAndLogAop {


    @Around("@annotation(lockAndLog)")
    public Object around(ProceedingJoinPoint joinPoint, LockAndLog lockAndLog) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        //数组的一维代表参数的索引，二维代表这个参数的注解索引
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        //带特定注解的参数下标集合
        Integer lockKeyIndex = null;
        flag:
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (Annotation an : parameterAnnotations[i]) {
                if (an instanceof LockKeyId) {
                    lockKeyIndex = i;
                    break flag;
                }
            }
        }

        if (lockKeyIndex == null) {
            throw new IllegalArgumentException("注解使用错误，未配置LockKeyId注解");
        }

        //传入下标可以取到参数值
        Object[] args = joinPoint.getArgs();
        Object lockKeyId = args[lockKeyIndex];
        if (lockKeyId == null || StringUtils.isBlank(lockKeyId.toString())) {
            throw new IllegalArgumentException("注解使用错误，LockKeyId为空");
        }

        String methodName = joinPoint.getSignature().getName();
        //需要获得自定义prefix以及key
        String taskName = lockAndLog.value();

        String redisKey = KeyGenerateUtil.methodLockKey(taskName, lockKeyId.toString());


        return lockAndRun(joinPoint, methodName, redisKey, taskName);

    }

}
