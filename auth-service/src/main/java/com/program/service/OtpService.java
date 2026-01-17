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

    private static final String OTP_PREFIX = "OTP:";

    public OtpService(StringRedisTemplate redisTemplate, TwilioConfig twilioConfig) {
        this.redisTemplate = redisTemplate;
        this.twilioConfig = twilioConfig;
    }

    private String otpKey(String phone) {
        return OTP_PREFIX + phone;
    }

    public void sendOtp(String phone) {

        String otp = String.valueOf(100000 + new Random().nextInt(900000));

        redisTemplate.opsForValue()
                .set(otpKey(phone), otp, 5, TimeUnit.MINUTES);

        Message.creator(
                new PhoneNumber(phone),
                new PhoneNumber(twilioConfig.getFromNumber()),
                "Your OTP is: " + otp
        ).create();
    }

    public void verifyOtp(String phone, String otp) {

        String cachedOtp = redisTemplate.opsForValue().get(otpKey(phone));

        if (cachedOtp == null) {
            throw new UnauthorizedException("OTP expired. Please request again.");
        }

        if (!cachedOtp.equals(otp)) {
            throw new UnauthorizedException("Invalid OTP");
        }

        redisTemplate.delete(otpKey(phone));
    }
}

