package com.hubert.mangocms.domain.requests.entities;

import com.hubert.mangocms.domain.models.entities.fields.definition.FieldType;

public record CreateFieldDefinition(
        String name,
        boolean isNullable,
        FieldType type
) {
}
