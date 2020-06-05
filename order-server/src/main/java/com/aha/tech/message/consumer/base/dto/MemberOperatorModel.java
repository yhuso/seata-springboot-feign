package com.aha.tech.message.consumer.base.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 描述:
 *
 * @Auther: bianjie
 * @Date: 2019-09-15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberOperatorModel {
    private String mobile;

    private Integer subscribeStatus;//1订阅，2未订阅

}
