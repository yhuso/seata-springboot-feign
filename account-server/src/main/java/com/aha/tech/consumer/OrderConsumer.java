package com.aha.tech.consumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "seata-order", path = "/")
public interface OrderConsumer {


    @GetMapping(path = "/order/create")
    public String getOrders(@RequestParam("id") Long id);
}
