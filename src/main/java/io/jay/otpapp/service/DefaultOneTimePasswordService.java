package io.jay.otpapp.service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import io.jay.otpapp.config.TwilioConfiguration;
import io.jay.otpapp.dto.OTPStatus;
import io.jay.otpapp.dto.PasswordResetRequest;
import io.jay.otpapp.dto.PasswordResetResponse;
import io.jay.otpapp.exception.NoOneTimePasswordException;
import io.jay.otpapp.exception.WrongOneTimePasswordException;
import io.jay.otpapp.repository.OneTimePasswordRepository;
import io.jay.otpapp.repository.entity.OneTimePasswordEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class DefaultOneTimePasswordService implements OneTimePasswordService {

    private final TwilioConfiguration twilioConfig;
    private final OneTimePasswordRepository repository;

    @Override
    public Mono<PasswordResetResponse> sendForPasswordReset(PasswordResetRequest request) {
        PhoneNumber to = new PhoneNumber(request.getPhoneNumber());
        PhoneNumber from = new PhoneNumber(twilioConfig.getPhoneNumber());
        String otp = generateOTP();
        String body = String.format("Dear customer, your OTP is %s", otp);

        try {
            Message.creator(to, from, body)
                    .create();

            repository.save(OneTimePasswordEntity.builder()
                    .username(request.getUsername())
                    .otp(otp)
                    .dateSent(Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC)))
                    .build());

            return Mono.just(PasswordResetResponse.builder()
                    .status(OTPStatus.DELIVERED)
                    .message(body)
                    .build());
        } catch (Exception ex) {
            return Mono.just(PasswordResetResponse.builder()
                    .status(OTPStatus.FAILED)
                    .message(ex.getMessage())
                    .build());
        }
    }

    @Override
    public Mono<String> validate(String username, String userInputOtp) {
        var row = repository.findById(username);
        if (!row.isPresent()) {
            return Mono.error(new NoOneTimePasswordException("No data found for this username"));
        }

        var storedOtp = row.get().getOtp();
        if (userInputOtp.equals(storedOtp)) {
            repository.deleteById(username);
            return Mono.just("Valid OTP.");
        } else {
            return Mono.error(new WrongOneTimePasswordException("Invalid OTP. Please retry."));
        }
    }

    private String generateOTP() {
        return new DecimalFormat("000000")
                .format(new Random().nextInt(999999));
    }
}
