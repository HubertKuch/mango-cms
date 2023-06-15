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

create table entity_model
(
    id             VARCHAR(36) NOT NULL PRIMARY KEY,
    name           text        not null,
    application_id varchar(36) not null references applications (id)
);

create table entity_field_definition
(
    id          VARCHAR(36) NOT NULL PRIMARY KEY,
    name        text        not null,
    type        text        not null,
    is_nullable bool        not null default false,
    entity_id   varchar(36) not null references entity_model (id)
);

create table entity_field_representation
(
    id            VARCHAR(36) NOT NULL PRIMARY KEY,
    value         text,
    definition_id varchar(36) not null references entity_field_definition (id)
);

CREATE TABLE application_keys
(
    `unique`       VARCHAR(10) NOT NULL,
    api_key        VARCHAR(36) NOT NULL,
    application_id VARCHAR(36) NOT NULL,
    CONSTRAINT `unq_app_key` UNIQUE (api_key, `unique`)
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
