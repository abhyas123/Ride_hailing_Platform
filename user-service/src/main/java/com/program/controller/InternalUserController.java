package com.program.controller;

import com.program.dto.CreateUserRequest;
import com.program.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/internal")
public class InternalUserController {

    private final UserService service;

    public InternalUserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> create(@Valid @RequestBody CreateUserRequest req) {
        service.createUserIfNotExists(req.getPhone());
        return ResponseEntity.ok().build();
    }
}
