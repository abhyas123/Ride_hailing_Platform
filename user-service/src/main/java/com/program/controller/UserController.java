package com.program.controller;

import com.program.dto.UpdateUserRequest;
import com.program.dto.UserResponse;
import com.program.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/me")
    public UserResponse me(@RequestHeader("X-User-Phone") String phone) {
        return service.getProfile(phone);
    }

    @PutMapping("/me")
    public UserResponse update(
            @RequestHeader("X-User-Phone") String phone,
            @Valid @RequestBody UpdateUserRequest req) {
        return service.updateProfile(phone, req);
    }
}
