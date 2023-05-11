package com.hubert.mangocms.domain.exceptions.internal;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthenticationException extends Exception {
    public AuthenticationException(String message) {
        super(message);
    }
}
