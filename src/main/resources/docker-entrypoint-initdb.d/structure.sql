SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE users
(
    id            VARCHAR(36) NOT NULL DEFAULT UUID() PRIMARY KEY,
    username      TEXT UNIQUE NOT NULL,
    password_hash TEXT        NOT NULL,
    registered_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    updated_at    TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP()
);

CREATE TRIGGER update_timestamp
    BEFORE UPDATE
    ON users
    FOR EACH ROW
    SET new.updated_at = CURRENT_TIMESTAMP();

CREATE TABLE applications
(
    id         VARCHAR(36) NOT NULL DEFAULT UUID() PRIMARY KEY,
    name       TEXT        NOT NULL,
    created_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    user_id    VARCHAR(36) NOT NULL REFERENCES users (id)
);

CREATE TABLE application_keys
(
    id       VARCHAR(36)        NOT NULL PRIMARY KEY,
    `unique` VARCHAR(10) UNIQUE NOT NULL,
    api_key  VARCHAR(36) UNIQUE NOT NULL,
    UNIQUE INDEX `IDX_unique` (`unique` ASC),
    UNIQUE INDEX `IDX_unique` (`api_key` ASC)
);

CREATE TABLE blogs
(
    id             VARCHAR(36) NOT NULL PRIMARY KEY DEFAULT UUID(),
    application_id VARCHAR(36) REFERENCES applications (id)
);

CREATE TABLE application_field_definitions
(
    id      VARCHAR(36) NOT NULL PRIMARY KEY DEFAULT UUID(),
    name    TEXT        NOT NULL,
    type    TEXT        NOT NULL,
    blog_id VARCHAR(36) REFERENCES blogs (id)
        ON DELETE CASCADE
);

CREATE TABLE blog_field_representation
(
    id                  VARCHAR(36) NOT NULL PRIMARY KEY DEFAULT UUID(),
    value               TEXT        NOT NULL,
    field_definition_id VARCHAR(36) REFERENCES blog_field_definitions (id)
)
