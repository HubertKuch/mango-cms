package com.hubert.mangocms.domain.exceptions.user;

import com.hubert.mangocms.domain.exceptions.ConflictException;

public class UserExistsException extends ConflictException {
    public UserExistsException(String message) {
        super(message);
    }
}
