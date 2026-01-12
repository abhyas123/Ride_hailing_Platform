package com.program.service;

import com.program.client.DriverClient;
import com.program.client.UserClient;
import com.program.dto.AuthResponse;
import com.program.entity.AuthUser;
import com.program.repository.AuthUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {

    private final AuthUserRepository repository;
    private final JwtService jwtService;
    private final UserClient userClient;
    private final DriverClient driverClient;

    public AuthService(AuthUserRepository repository,
                       JwtService jwtService,
                       UserClient userClient,
                       DriverClient driverClient) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.userClient = userClient;
        this.driverClient = driverClient;
    }

    public AuthResponse loginOrRegister(String phone, String role) {

        AuthUser user = repository.findByPhone(phone)
                .orElseGet(() -> {
                    AuthUser u = new AuthUser();
                    u.setPhone(phone);
                    u.setRole(role == null ? "RIDER" : role);
                    return repository.save(u);
                });

        if ("RIDER".equals(user.getRole())) {
            userClient.createUser(phone);
        } else {
            driverClient.createDriver(phone);
        }

        return new AuthResponse(
                jwtService.generateToken(user),
                user.getRole()
        );
    }
}
