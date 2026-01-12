package com.program.client;


import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public void createUser(String phone) {
        // DEV MODE – just fire and forget
        restTemplate.postForObject(
                "http://user-service/users/internal/create",
                phone,
                Void.class
        );
    }
}

