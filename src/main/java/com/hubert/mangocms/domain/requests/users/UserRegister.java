package com.hubert.mangocms.domain.requests.users;

import com.hubert.mangocms.domain.requests.users.credentials.RepeatPasswordCredentials;

public record UserRegister(
        String username,
        RepeatPasswordCredentials password
) {}
