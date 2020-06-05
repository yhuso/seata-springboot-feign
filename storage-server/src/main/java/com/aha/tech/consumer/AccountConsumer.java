package com.aha.tech.consumer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "seata-accountserver", path = "/")
public interface AccountConsumer {




    @PostMapping(path = "/decrease")
    public int testFeign(@RequestBody Account account);
}
