package com.hubert.mangocms.domain.exceptions.internal;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends Exception {
    public ConflictException(String message) {
        super(message);
    }
}
