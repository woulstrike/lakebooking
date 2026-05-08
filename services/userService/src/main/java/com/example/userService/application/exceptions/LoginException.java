package com.example.userService.application.exceptions;

public class LoginException extends RuntimeException
{
    public LoginException(String message) {
        super(message);
    }
}
