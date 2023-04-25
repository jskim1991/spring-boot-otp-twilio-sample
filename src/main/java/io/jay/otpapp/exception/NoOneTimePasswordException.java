package io.jay.otpapp.exception;

public class NoOneTimePasswordException extends RuntimeException {

    public NoOneTimePasswordException(String message) {
        super(message);
    }
}
