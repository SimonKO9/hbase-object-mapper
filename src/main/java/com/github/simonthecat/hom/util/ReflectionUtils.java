package com.github.simonthecat.hom.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ReflectionUtils {

    public static Set<Field> getFieldsWithAnnotations(Class<?> clazz, List<Class<? extends Annotation>> annotations) {
        Set<Field> set = new HashSet<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (getFirstAnnotationPresent(field, annotations).isPresent()) {
                set.add(field);
            }
        }

        return set;
    }

    public static Optional<Annotation> getFirstAnnotationPresent(Field field, List<Class<? extends Annotation>> annotations) {
        for (Class<? extends Annotation> annotation : annotations) {
            if (field.isAnnotationPresent(annotation)) {
                return Optional.of(field.getDeclaredAnnotation(annotation));
            }
        }
        return Optional.empty();
    }
}
