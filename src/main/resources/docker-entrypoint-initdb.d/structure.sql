SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE users
(
    id            VARCHAR(36) NOT NULL PRIMARY KEY,
    username      TEXT        NOT NULL,
    password_hash TEXT        NOT NULL,
    registered_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    updated_at    TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP()
);

CREATE TABLE applications
(
    id         VARCHAR(36) NOT NULL PRIMARY KEY,
    name       TEXT        NOT NULL,
    created_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    updated_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    user_id    VARCHAR(36) NOT NULL REFERENCES users (id)
);

CREATE TABLE application_field_definitions
(
    id             VARCHAR(36) NOT NULL PRIMARY KEY,
    name           TEXT        NOT NULL,
    is_required    BOOLEAN     NOT NULL DEFAULT FALSE,
    type           TEXT        NOT NULL,
    default_value  TEXT,
    application_id VARCHAR(36) NOT NULL REFERENCES applications (id)
);

CREATE TABLE application_keys
(
    id             VARCHAR(36) NOT NULL PRIMARY KEY,
    `unique`       VARCHAR(10) NOT NULL,
    api_key        VARCHAR(36) NOT NULL,
    application_id VARCHAR(36) NOT NULL REFERENCES applications (id),
    CONSTRAINT `unq_app_key` UNIQUE (api_key, `unique`)
);

ALTER TABLE application_keys
    ADD CONSTRAINT `unq_app_key` UNIQUE (api_key, `unique`);

CREATE TABLE blogs
(
    id             VARCHAR(36) NOT NULL PRIMARY KEY,
    created_at     TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    updated_at     TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP() ON UPDATE CURRENT_TIMESTAMP(),
    application_id VARCHAR(36) NOT NULL REFERENCES applications (id)
        ON DELETE CASCADE
);

CREATE TABLE blog_field_representation
(
    id            VARCHAR(36) NOT NULL PRIMARY KEY,
    value         TEXT,
    definition_id VARCHAR(36) NOT NULL REFERENCES application_field_definitions (id),
    blog_id       VARCHAR(36) NOT NULL REFERENCES blogs (id)
);

CREATE TABLE assets
(
    id             VARCHAR(36) NOT NULL PRIMARY KEY,
    name           TEXT        NOT NULL,
    mime           TEXT        NOT NULL,
    size_in_bytes  BIGINT      NOT NULL,
    application_id VARCHAR(36) NOT NULL REFERENCES applications (id)
);

SET FOREIGN_KEY_CHECKS = 1;
