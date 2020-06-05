package com.aha.tech.task.aop;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TaskRockAndLog {
    /**
     * 任务名
     *
     * @return
     */
    String value() default "";

}
