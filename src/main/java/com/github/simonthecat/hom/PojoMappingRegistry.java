package com.github.simonthecat.hom;

import com.github.simonthecat.hom.model.CellDef;
import com.github.simonthecat.hom.model.RowKey;
import com.github.simonthecat.hom.util.ReflectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public class PojoMappingRegistry {

    private static final Log LOG = LogFactory.getLog(PojoMappingRegistry.class);

    private Map<Class<?>, Set<FieldMapping>> mappings = new HashMap<>();

    public PojoMappingRegistry() {
    }

    public Set<FieldMapping> get(Class<?> clazz) {
        if (!mappings.containsKey(clazz)) {
            mappings.put(clazz, scanPojo(clazz));
        }

        return mappings.get(clazz);
    }

    private Set<FieldMapping> scanPojo(Class<?> clazz) {
        List<Class<? extends Annotation>> annotations = Arrays.asList(CellDef.class, RowKey.class);
        Set<Field> annotatedFields = ReflectionUtils.getFieldsWithAnnotations(clazz, annotations);

        Set<FieldMapping> fieldMappings = new HashSet<>();
        for (Field field : annotatedFields) {
            Annotation annotation = ReflectionUtils.getFirstAnnotationPresent(field, annotations).get();
            FieldMapping fieldMapping = toFieldMapping(field, annotation);
            fieldMappings.add(fieldMapping);
        }
        return fieldMappings;
    }

    private FieldMapping toFieldMapping(Field field, Annotation annotation) {
        if(annotation instanceof CellDef) {
            CellDef cellDef = (CellDef) annotation;
            ResultValueExtractor.ResultCellExtractor extractor = new ResultValueExtractor.ResultCellExtractor(cellDef.family(), cellDef.qualifier());
            return new FieldMapping(field.getName(), field.getType(), extractor);
        } else if(annotation instanceof RowKey) {
            return new FieldMapping(field.getName(), field.getType(), ResultValueExtractor.RowKeyExtractor.INSTANCE);
        }

        throw new RuntimeException("Unknown annotation: " + annotation.getClass());
    }

}
