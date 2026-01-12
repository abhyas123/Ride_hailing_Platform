package com.program.service;

import com.program.config.TwilioConfig;
import com.program.exception.UnauthorizedException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OtpService {

    private final StringRedisTemplate redisTemplate;
    private final TwilioConfig twilioConfig;

    public OtpService(StringRedisTemplate redisTemplate, TwilioConfig twilioConfig) {
        this.redisTemplate = redisTemplate;
        this.twilioConfig = twilioConfig;
    }

    public void sendOtp(String phone) {
        String otp = String.valueOf(100000 + new Random().nextInt(900000));
        redisTemplate.opsForValue().set(phone, otp, 5, TimeUnit.MINUTES);

        Message.creator(
                new PhoneNumber(phone),
                new PhoneNumber(twilioConfig.getFromNumber()),
                "Your OTP is: " + otp
        ).create();
    }

    public void verifyOtp(String phone, String otp) {
        String cached = redisTemplate.opsForValue().get(phone);
        if (cached == null || !cached.equals(otp)) {
            throw new UnauthorizedException("Invalid or expired OTP");
        }
        redisTemplate.delete(phone);
    }
}
