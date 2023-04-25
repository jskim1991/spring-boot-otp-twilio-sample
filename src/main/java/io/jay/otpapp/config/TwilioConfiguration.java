package io.jay.otpapp.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "twilio.account")
@Data
public class TwilioConfiguration {
    private String sid;
    private String authToken;
    private String phoneNumber;
}
