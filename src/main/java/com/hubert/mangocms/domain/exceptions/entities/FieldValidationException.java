package com.hubert.mangocms.domain.exceptions.entities;

import com.hubert.mangocms.domain.exceptions.internal.ConflictException;

public class FieldValidationException extends ConflictException {
    public FieldValidationException(String message) {
        super(message);
    }
}
