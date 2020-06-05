package com.aha.tech.task.aop;

import com.aha.tech.utils.KeyGenerateUtil;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TaskRockAndLogAop extends BaseLockAndLogAop {


    @Around("@annotation(taskRockAndLog)&& @annotation(org.springframework.scheduling.annotation.Scheduled)")
    public Object around(ProceedingJoinPoint joinPoint, TaskRockAndLog taskRockAndLog) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String redisKey = KeyGenerateUtil.taskRockKey(className, methodName);
        String taskName = taskRockAndLog.value();
        if (StringUtils.isBlank(taskName)) {
            taskName = className + "." + methodName;
        }

        return lockAndRun(joinPoint, methodName, redisKey, taskName);

    }

}
