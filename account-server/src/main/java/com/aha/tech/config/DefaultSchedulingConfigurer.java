package com.aha.tech.config;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.scheduling.SchedulingException;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * @author: ahaschool
 * @date: 2019-06-11 15:45
 */
@Component
public class DefaultSchedulingConfigurer implements SchedulingConfigurer {
    private ScheduledTaskRegistrar taskRegistrar;
    private Set<ScheduledFuture<?>> scheduledFutures = null;
    private Map<String, ScheduledFuture<?>> taskFutures = new ConcurrentHashMap<>();
    private Map<String, String> taskCronExpressionMap = new ConcurrentHashMap<>();

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        this.taskRegistrar = taskRegistrar;
    }

    @SuppressWarnings("unchecked")
    private Set<ScheduledFuture<?>> getScheduledFutures() {
        if (scheduledFutures == null) {
            try {
                scheduledFutures = (Set<ScheduledFuture<?>>) FieldUtils.readDeclaredField(taskRegistrar, "scheduledTasks", true);
            } catch (IllegalAccessException e) {
                throw new SchedulingException("not found scheduledFutures field.");
            }
        }
        return scheduledFutures;
    }

    /**
     * 添加任务
     */
    public void addCronTask(String taskId, CronTask cronTask) {
        if (taskFutures.containsKey(taskId)) {
            throw new SchedulingException("the taskId[" + taskId + "] was added.");
        }
        TaskScheduler scheduler = taskRegistrar.getScheduler();
        Assert.notNull(scheduler, "scheduler must be not null!");

        ScheduledFuture<?> future = scheduler.schedule(cronTask.getRunnable(), cronTask.getTrigger());
        getScheduledFutures().add(future);
        taskFutures.put(taskId, future);
        taskCronExpressionMap.put(taskId, cronTask.getExpression());

    }

    /**
     * 取消任务
     */
    public void cancelTriggerTask(String taskId) {
        ScheduledFuture<?> future = taskFutures.get(taskId);
        if (future != null) {
            future.cancel(true);
        }
        taskFutures.remove(taskId);
        taskCronExpressionMap.remove(taskId);
        getScheduledFutures().remove(future);
    }


    /**
     * 获取任务cron 表达式
     *
     * @param taskId
     * @return
     */
    public String getTaskCron(String taskId) {
        return taskCronExpressionMap.get(taskId);
    }

    /**
     * 任务编号
     */
    public Set<String> taskIds() {
        return taskFutures.keySet();
    }


}