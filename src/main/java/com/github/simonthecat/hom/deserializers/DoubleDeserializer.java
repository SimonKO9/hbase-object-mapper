package com.github.simonthecat.hom.deserializers;

import com.github.simonthecat.hom.DeserializationException;
import com.github.simonthecat.hom.TypeDeserializer;
import org.apache.hadoop.hbase.util.Bytes;

public class DoubleDeserializer implements TypeDeserializer<Double> {
    @Override
    public Double deserialize(byte[] bytes) throws DeserializationException {
        return Bytes.toDouble(bytes);
    }
}
