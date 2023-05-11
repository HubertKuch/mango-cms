package com.hubert.mangocms.domain.requests.application;

import com.hubert.mangocms.domain.models.blog.fields.FieldType;

public record CreateDefinition(String name, boolean isRequired, FieldType type) {}
