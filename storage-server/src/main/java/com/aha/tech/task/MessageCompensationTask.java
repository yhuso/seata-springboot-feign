package com.aha.tech.task;

import com.aha.tech.service.MessageLogCompensationService;
import com.aha.tech.service.MessageLogService;
import com.aha.tech.task.aop.TaskRockAndLog;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author: ahaschool
 * @date: 2019-06-17 10:13
 */
//@Component
public class MessageCompensationTask {
    private final MessageLogCompensationService messageLogService;

    public MessageCompensationTask(MessageLogCompensationService messageLogService) {
        this.messageLogService = messageLogService;
    }

    @Scheduled(cron = "${MessageCompensationTask.cron:0 1 * * * ?}")
    @TaskRockAndLog
    public void messageCompensation() {
        messageLogService.messageCompensation();
    }

}
