package com.aha.tech.memberserver.facade.constants;

/**
 * created by tangkun on 2019-05-23
 */
public class MessageLogStatus {

    //消息消费状态，0未消费，1已消费 ,3 重试失败 4.处理中
    public static final int PROCESS_NOT = 0;
    public static final int PROCESSED = 1;
    public static final int FAIL = 3;


    public static final int PROCESSING = 4;

}
