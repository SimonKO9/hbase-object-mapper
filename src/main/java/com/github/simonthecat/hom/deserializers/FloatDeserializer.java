package com.github.simonthecat.hom.deserializers;

import com.github.simonthecat.hom.DeserializationException;
import com.github.simonthecat.hom.TypeDeserializer;
import org.apache.hadoop.hbase.util.Bytes;

public class FloatDeserializer implements TypeDeserializer<Float> {
    @Override
    public Float deserialize(byte[] bytes) throws DeserializationException {
        return Bytes.toFloat(bytes);
    }
}
