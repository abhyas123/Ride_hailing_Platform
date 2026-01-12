package com.program.controller;

import com.program.dto.AuthResponse;
import com.program.dto.SubmitPhoneRequest;
import com.program.dto.VerifyOtpRequest;
import com.program.service.AuthService;
import com.program.service.OtpService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final OtpService otpService;
    private final AuthService authService;

    public AuthController(OtpService otpService, AuthService authService) {
        this.otpService = otpService;
        this.authService = authService;
    }

    @PostMapping("/submit-phone")
    public ResponseEntity<String> submitPhone(@Valid @RequestBody SubmitPhoneRequest req) {
        otpService.sendOtp(req.getPhone());
        return ResponseEntity.ok("OTP sent");
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<String> resendOtp(@RequestBody SubmitPhoneRequest req) {
        otpService.sendOtp(req.getPhone());
        return ResponseEntity.ok("OTP resent");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<AuthResponse> verifyOtp(@Valid @RequestBody VerifyOtpRequest req) {
        otpService.verifyOtp(req.getPhone(), req.getOtp());
        return ResponseEntity.ok(
                authService.loginOrRegister(req.getPhone(), "RIDER")
        );
    }
}
