package com.program.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "admin-service",
        configuration = com.program.config.FeignConfig.class
)
public interface AdminServiceClient {

    @GetMapping("/admin/internal/platform-fee")
    Double getPlatformFeePercentage();
}
