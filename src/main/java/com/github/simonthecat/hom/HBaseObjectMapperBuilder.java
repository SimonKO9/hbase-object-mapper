package com.github.simonthecat.hom;

import com.github.simonthecat.hom.deserializers.*;

public final class HBaseObjectMapperBuilder {

    TypeDeserializerRegistry registry = new TypeDeserializerRegistry();

    private HBaseObjectMapperBuilder() {
    }

    public static HBaseObjectMapperBuilder builder() {
        return new HBaseObjectMapperBuilder();
    }

    public <T> HBaseObjectMapperBuilder registerTypeDeserializer(Class<T> clazz, TypeDeserializer<T> deserializer) {
        registry.register(clazz, deserializer);
        return this;
    }

    public HBaseObjectMapperBuilder withDefaultDeserializers() {
        registerTypeDeserializer(String.class, new StringDeserializer());
        registerTypeDeserializer(Double.class, new DoubleDeserializer());
        registerTypeDeserializer(Integer.class, new IntegerDeserializer());
        registerTypeDeserializer(Long.class, new LongDeserializer());
        registerTypeDeserializer(Float.class, new FloatDeserializer());
        return this;
    }

    public HBaseObjectMapper build() {
        return new HBaseObjectMapper(registry);
    }


}
