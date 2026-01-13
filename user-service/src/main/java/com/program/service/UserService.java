package com.program.service;

import com.program.dto.UpdateUserRequest;
import com.program.dto.UserResponse;
import com.program.entity.User;
import com.program.exception.UserNotFoundException;
import com.program.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User createUserIfNotExists(String phone) {
        return repository.findByPhone(phone)
                .orElseGet(() -> {
                    User user = new User();
                    user.setPhone(phone);
                    log.info("Creating new user with phone {}", phone);
                    return repository.save(user);
                });
    }

    public UserResponse getProfile(String phone) {
        User user = repository.findByPhone(phone)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return new UserResponse(user.getId(), user.getPhone(), user.getName());
    }

    public UserResponse updateProfile(String phone, UpdateUserRequest req) {
        User user = repository.findByPhone(phone)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setName(req.getName());
        return new UserResponse(user.getId(), user.getPhone(), user.getName());
    }
}
