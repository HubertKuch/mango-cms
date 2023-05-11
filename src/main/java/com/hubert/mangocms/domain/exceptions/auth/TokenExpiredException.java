package com.hubert.mangocms.domain.exceptions.auth;

import com.hubert.mangocms.domain.exceptions.internal.AuthenticationException;

public class TokenExpiredException extends AuthenticationException {
    public TokenExpiredException() {
        super("Token expired. Log in again.");
    }
}
