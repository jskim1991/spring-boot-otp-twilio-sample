package io.jay.otpapp.service;


import io.jay.otpapp.dto.PasswordResetRequest;
import io.jay.otpapp.dto.PasswordResetResponse;
import reactor.core.publisher.Mono;

public interface OneTimePasswordService {
    Mono<PasswordResetResponse> sendForPasswordReset(PasswordResetRequest request);

    Mono<String> validate(String username, String userInputOtp);
}
