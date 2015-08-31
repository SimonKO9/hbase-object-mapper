package com.github.simonthecat.hom;

public interface TypeDeserializer<T> {

    T deserialize(byte[] bytes) throws DeserializationException;

}
