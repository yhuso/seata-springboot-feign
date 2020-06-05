package com.aha.tech.controller;

import com.aha.tech.model.entity.Account;
import com.aha.tech.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    private AccountService accountService;

    @PostMapping(path = "/decrease")
    public int testFeign(@RequestBody Account account) {
        return accountService.decreaseAccount(account);
    }
}
