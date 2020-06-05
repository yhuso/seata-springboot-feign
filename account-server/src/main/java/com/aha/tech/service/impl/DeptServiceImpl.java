package com.aha.tech.service.impl;

import com.aha.tech.consumer.OrderConsumer;

import org.bouncycastle.cert.ocsp.RespData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.seata.spring.annotation.GlobalTransactional;

@RestController
public class DeptServiceImpl  {

//    @Autowired
//    BankServiceFeign bankServiceFeign;
//
//    @Override
//    @GlobalTransactional(rollbackFor = Exception.class)
//    public RespData addDept() {
//        Dept dept = new Dept();
//        dept.setDeptName("asdasdsa");
//        dept.setDeptPid(0L);
//        dept.setCreateTime(DateUtils.getCurrentDate("", String.class));
//        if(dept.insert()){
//            bankServiceFeign.tran();
//        }
//        return RespData.success();
//    }
    @Autowired
    private OrderConsumer orderConsumer;

    @GetMapping(path = "/test")
    public String testFeign() {
        String orders = orderConsumer.getOrders(100L);
        return orders;
    }

}
