package com.aha.tech.service.impl;

import com.aha.tech.model.entity.Order;
import com.aha.tech.model.mapper.OrderEntityMapper;
import com.aha.tech.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 描述:
 *
 * @Auther: bianjie
 * @Date: 2020/6/5
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderEntityMapper orderEntityMapper;

    @Override
    public void add(int money) {
        Order order = new Order();
        order.setUserId(UUID.randomUUID().toString());
        order.setMoney(money);


        orderEntityMapper.insertSelective(order);
    }
}
