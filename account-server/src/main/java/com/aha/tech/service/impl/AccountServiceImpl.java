package com.aha.tech.service.impl;

import com.aha.tech.model.entity.Account;
import com.aha.tech.model.mapper.AccountEntityMapper;
import com.aha.tech.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述:
 *
 * @Auther: bianjie
 * @Date: 2020/6/5
 */
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountEntityMapper accountEntityMapper;

    @Override
    public int decreaseAccount(Account accountDTO) {
        accountEntityMapper.decreaseAccount(accountDTO.getMoney(),accountDTO.getUserId());
        return 100;
    }
}
