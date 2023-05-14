package com.hubert.mangocms.domain.responses;

import com.hubert.mangocms.domain.models.blog.fields.FieldType;

public record AggregatedField(String name, Object value, FieldType type, boolean isRequired, Object defaultValue) {}
