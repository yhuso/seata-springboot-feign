package com.aha.tech.task.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 需要与{@link LockKeyId}结合使用
 * 构造分布式锁的key需要两个参数，
 * @see com.aha.tech.utils.KeyGenerateUtil#methodLockKey(String keyPrefix, String key)
 * 分别由以下两部分组成
 * {@link LockAndLog}的value 作为keyPrefix
 * {@link LockKeyId}注解的参数值 作为key
 *
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LockAndLog {
    /**
     * 任务名
     *
     * @return
     */
    String value();

}
