package com.program.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(
        name = "user-service",
        path = "/users/internal"
)
public interface UserServiceClient {

    @GetMapping("/{userId}/phone")
    String getUserPhone(
            @PathVariable("userId") UUID userId
    );

    @GetMapping("/{userId}/fcm-token")
    String getUserFcmToken(
            @PathVariable("userId") UUID userId
    );
}
