package io.jay.otpapp.resource;

import io.jay.otpapp.resource.handler.OneTimePasswordHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class RouterConfiguration {
    private final OneTimePasswordHandler handler;

    @Bean
    public RouterFunction<ServerResponse> handleSMS() {
        return RouterFunctions.route()
                .POST("/router/send", handler::send)
                .POST("/router/validate", handler::validate)
                .build();
    }
}
