package com.github.simonthecat.hom;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode
public class FieldMapping {
    private String fieldName;
    private Class<?> fieldType;
    private ResultValueExtractor valueExtractor;
}
