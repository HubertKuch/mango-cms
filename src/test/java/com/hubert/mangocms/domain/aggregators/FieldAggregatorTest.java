package com.hubert.mangocms.domain.aggregators;

import com.hubert.mangocms.domain.models.app.ApplicationFieldDefinition;
import com.hubert.mangocms.domain.models.blog.fields.ApplicationBlogFieldRepresentation;
import com.hubert.mangocms.domain.models.blog.fields.FieldType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class FieldAggregatorTest {

    FieldAggregator fieldAggregator = new FieldAggregator();

    @Test
    void givenIntegerField_shouldParseToInt() {
        ApplicationBlogFieldRepresentation fieldRepresentation = new ApplicationBlogFieldRepresentation("24",
                new ApplicationFieldDefinition("age", "", false, FieldType.INTEGER, null),
                null
        );

        assertAll(() -> assertInstanceOf(Integer.class, fieldAggregator.aggregateField(fieldRepresentation).value()));
    }

    @Test
    void givenBooleanField_shouldParseToBoolean() {
        ApplicationBlogFieldRepresentation fieldRepresentation = new ApplicationBlogFieldRepresentation("false",
                new ApplicationFieldDefinition("true", "", false, FieldType.BOOLEAN, null),
                null
        );

        assertAll(() -> assertInstanceOf(Boolean.class, fieldAggregator.aggregateField(fieldRepresentation).value()));
    }

    @Test
    void givenRealField_shouldParseToReal() {
        ApplicationBlogFieldRepresentation fieldRepresentation = new ApplicationBlogFieldRepresentation("24",
                new ApplicationFieldDefinition("age", "", false, FieldType.REAL, null),
                null
        );

        assertAll(() -> assertInstanceOf(Double.class, fieldAggregator.aggregateField(fieldRepresentation).value()));
    }

    @Test
    void givenStringField_shouldParseToString() {
        ApplicationBlogFieldRepresentation fieldRepresentation = new ApplicationBlogFieldRepresentation("test",
                new ApplicationFieldDefinition("age", "", false, FieldType.TEXT, null),
                null
        );

        assertAll(() -> assertInstanceOf(String.class, fieldAggregator.aggregateField(fieldRepresentation).value()));
    }

    @Test
    void givenValueNotOfType_shouldReturnAsString() {
        ApplicationBlogFieldRepresentation fieldRepresentation = new ApplicationBlogFieldRepresentation("test",
                new ApplicationFieldDefinition("age", "", false, FieldType.REAL, null),
                null
        );

        assertAll(() -> assertInstanceOf(String.class, fieldAggregator.aggregateField(fieldRepresentation).value()));
    }
}