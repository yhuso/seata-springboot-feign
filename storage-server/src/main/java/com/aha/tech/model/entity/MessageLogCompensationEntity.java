package com.aha.tech.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "h_message_log_compensation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageLogCompensationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;

    private String topic;

    /**
     * 消息内容
     */
    private String body;

    private Integer status;

    @Column(name = "process_bean")
    private String processBean;

    @Column(name = "process_method")
    private String processMethod;

    @Column(name = "retry_num")
    private Integer retryNum;

}