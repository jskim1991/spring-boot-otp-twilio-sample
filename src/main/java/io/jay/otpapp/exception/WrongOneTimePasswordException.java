package io.jay.otpapp.exception;

public class WrongOneTimePasswordException extends RuntimeException {
    public WrongOneTimePasswordException(String message) {
        super(message);
    }
}
