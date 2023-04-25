package io.jay.otpapp;

import com.twilio.Twilio;
import io.jay.otpapp.config.TwilioConfiguration;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.Assert;

@SpringBootApplication
@RequiredArgsConstructor
public class MainApplication {

    private final TwilioConfiguration twilioConfig;

    @PostConstruct
    public void initTwilio() {
        Assert.hasText(twilioConfig.getSid(), "twilio.account.sid must not be empty");
        Assert.hasText(twilioConfig.getAuthToken(), "twilio.account.authToken must not be empty");
        Assert.hasText(twilioConfig.getPhoneNumber(), "twilio.account.phoneNumber must not be empty");
        Twilio.init(twilioConfig.getSid(), twilioConfig.getAuthToken());
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

}
