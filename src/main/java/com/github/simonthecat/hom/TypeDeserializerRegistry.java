package com.github.simonthecat.hom;

import java.util.HashMap;
import java.util.Map;

public class TypeDeserializerRegistry {

    private Map<Class<?>, TypeDeserializer<?>> deserializers = new HashMap<>();

    public TypeDeserializerRegistry() {
    }

    public <T> void register(Class<T> clazz, TypeDeserializer<T> deserializer) {
        deserializers.put(clazz, deserializer);
    }

    public TypeDeserializer<?> getDeserializer(Class<?> clazz) {
        if (deserializers.containsKey(clazz)) {
            return deserializers.get(clazz);
        } else {
            throw new DeserializationException("Couldn't find deserializer for type " + clazz.getName());
        }
    }

}
