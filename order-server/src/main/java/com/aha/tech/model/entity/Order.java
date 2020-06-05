package com.aha.tech.model.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 描述:
 *
 * @Auther: bianjie
 * @Date: 2020/6/5
 */
@Data
@Table(name = "order_tbl")
public class Order {
    @ApiModelProperty(value = "id", name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(name="user_id")
    private String userId;

    @Column(name="commodity_code")
    private String commodityCode;

    @Column(name="money")
    private Integer money;

}
