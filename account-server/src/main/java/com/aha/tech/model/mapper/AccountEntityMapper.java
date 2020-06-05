package com.aha.tech.model.mapper;

import com.aha.tech.model.entity.Account;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.mapper.common.Mapper;

public interface AccountEntityMapper extends Mapper<Account> {


    void decreaseAccount(@Param("amount") Integer amount,
                         @Param("userId") String userId);

}
