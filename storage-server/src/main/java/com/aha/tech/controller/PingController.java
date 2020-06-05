package com.aha.tech.controller;

import com.aha.tech.service.GlobalService;
import com.aha.tech.service.impl.GlobalBizServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
public class PingController {

    @ApiOperation("ping")
    @GetMapping(path = "/ping")
    public String ping() {
        return "pong";
    }

    @Autowired
    private GlobalService globalService;

    @GetMapping(path = "/test")
    public String test(@RequestParam int id) {
        globalService.biz(id);
        return "finish";
    }

}
