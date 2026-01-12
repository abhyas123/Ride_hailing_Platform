package com.program.client;


import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DriverClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public void createDriver(String phone) {
        restTemplate.postForObject(
                "http://driver-service/drivers/internal/create",
                phone,
                Void.class
        );
    }
}

