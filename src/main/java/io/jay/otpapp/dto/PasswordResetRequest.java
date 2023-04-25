package io.jay.otpapp.dto;

import lombok.Data;

@Data
public class PasswordResetRequest {
    private String phoneNumber;
    private String username;
    private String otp;
}
