package io.jay.otpapp.route;

import io.jay.otpapp.dto.PasswordResetRequest;
import io.jay.otpapp.dto.PasswordResetResponse;
import io.jay.otpapp.route.handler.OneTimePasswordHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@RequiredArgsConstructor
public class RouterConfiguration {
    private final OneTimePasswordHandler handler;

    @Bean
    public WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }

    @Bean
    @RouterOperations({
            @RouterOperation(
                    path = "/router/send",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.POST,
                    beanClass = OneTimePasswordHandler.class,
                    beanMethod = "send",
                    operation = @Operation(description = "Send OTP via SMS",
                            operationId = "send", // mandatory,
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            content = @Content(schema = @Schema(implementation = PasswordResetResponse.class)))
                            },
                            requestBody = @RequestBody(
                                    content = @Content(schema = @Schema(implementation = PasswordResetRequest.class))
                            )
                    )
            ),
            @RouterOperation(
                    path = "/router/validate",
                    produces = {MediaType.APPLICATION_JSON_VALUE},
                    method = RequestMethod.POST,
                    beanClass = OneTimePasswordHandler.class,
                    beanMethod = "validate",
                    operation = @Operation(description = "Validate OTP",
                            operationId = "validate", // mandatory,
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            content = @Content(schema = @Schema(implementation = PasswordResetResponse.class)))
                            },
                            requestBody = @RequestBody(
                                    content = @Content(schema = @Schema(implementation = PasswordResetRequest.class))
                            )
                    )
            )
    })
    public RouterFunction<ServerResponse> handleSMS() {
        return RouterFunctions.route()
                .POST("/router/send", handler::send)
                .POST("/router/validate", handler::validate)
                .build();
    }
}
