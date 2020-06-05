package com.aha.tech.controller;

import com.aha.tech.service.OrderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class PingController {

    @ApiOperation("ping")
    @GetMapping(path = "/ping")
    public String ping() {
        return "pong";
    }


    @Autowired
    private OrderService orderService;

    @GetMapping(path = "/order/create")
    public String getOrders(@RequestParam("id") Long id) {
        log.info("收到参数:{}", id);
        orderService.add(id.intValue());
        return "order-ok";
    }
}
