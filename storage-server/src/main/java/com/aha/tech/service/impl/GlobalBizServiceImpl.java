package com.aha.tech.service.impl;

import com.aha.tech.consumer.Account;
import com.aha.tech.consumer.AccountConsumer;
import com.aha.tech.consumer.OrderConsumer;
import com.aha.tech.service.GlobalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;

/**
 * 描述:
 *
 * @Auther: bianjie
 * @Date: 2020/6/5
 */
@Slf4j
@Service
public class GlobalBizServiceImpl implements GlobalService {
    @Autowired
    private OrderConsumer orderConsumer;

    @Autowired
    private AccountConsumer accountConsumer;


    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public void biz(int id) {
        Account account = new Account();
        account.setUserId("U100001");
        account.setMoney(id);
        accountConsumer.testFeign(account);

        orderConsumer.getOrders((long)id);

        if(id % 3 == 0) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            throw new RuntimeException("test");
        }
    }
}
