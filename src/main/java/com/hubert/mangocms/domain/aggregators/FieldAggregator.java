package com.hubert.mangocms.domain.aggregators;

import com.hubert.mangocms.domain.models.blog.fields.ApplicationBlogFieldRepresentation;
import com.hubert.mangocms.domain.models.blog.fields.FieldType;
import com.hubert.mangocms.domain.responses.AggregatedField;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FieldAggregator {

    public AggregatedField aggregateField(
            ApplicationBlogFieldRepresentation representation
    ) {
        return new AggregatedField(
                representation.getDefinition().getName(),
                aggregateValueByType(representation.getValue(), representation.getDefinition().getType()),
                representation.getDefinition().getType(),
                representation.getDefinition().isRequired(),
                representation.getDefaultValue()
        );
    }

    private Object aggregateValueByType(String value, FieldType type) {
        try {
            return switch (type) {
                case INTEGER -> Integer.valueOf(value);
                case REAL -> Double.valueOf(value);
                case BOOLEAN -> Boolean.valueOf(value);
                default -> type;
            };
        } catch (Throwable throwable) {
            return value;
        }
    }

}
