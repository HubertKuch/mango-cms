package com.hubert.mangocms.domain.exceptions.entities;

import com.hubert.mangocms.domain.exceptions.internal.NotFoundException;

public class FieldDefinitionDoesntExistsException extends NotFoundException {
    public FieldDefinitionDoesntExistsException() {
        super("Field definition not found");
    }
}
