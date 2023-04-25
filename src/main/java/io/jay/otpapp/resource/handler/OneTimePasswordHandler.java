package io.jay.otpapp.resource.handler;

import io.jay.otpapp.dto.PasswordResetRequest;
import io.jay.otpapp.service.OneTimePasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class OneTimePasswordHandler {
    private final OneTimePasswordService service;

    public Mono<ServerResponse> send(ServerRequest request) {
        return request.bodyToMono(PasswordResetRequest.class)
                .flatMap(service::sendForPasswordReset)
                .flatMap(result -> ServerResponse
                        .status(HttpStatus.OK)
                        .bodyValue(result));
    }

    public Mono<ServerResponse> validate(ServerRequest request) {
        return request.bodyToMono(PasswordResetRequest.class)
                .flatMap(dto -> service.validate(dto.getUsername(), dto.getOtp()))
                .flatMap(result -> ServerResponse.status(HttpStatus.OK)
                        .bodyValue(result));
    }

}
