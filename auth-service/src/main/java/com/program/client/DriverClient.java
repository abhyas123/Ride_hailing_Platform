package com.program.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "driver-service")
public interface DriverClient {

    @PostMapping("/drivers/internal/create")
    void createDriver(@RequestBody String phone);
}
