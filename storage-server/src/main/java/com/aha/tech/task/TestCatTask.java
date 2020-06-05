//package com.aha.tech.task;
//
//import com.aha.tech.service.MemberContractService;
//import com.aha.tech.task.aop.TaskRockAndLog;
//
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import lombok.extern.slf4j.Slf4j;
//
///**
// * 代扣任务，线上每两分钟执行一次
// * @author: ahaschool
// * @date: 2019-05-24 10:18
// */
//@Slf4j
//@Component
//public class TestCatTask {
//
//
//    @TaskRockAndLog
//    @Scheduled(cron = "*/5 * * * * ?")
//    public void autoWithhold() {
//        log.info("test cat task,thread:{}", Thread.currentThread().getName());
//    }
//}
