package com.github.simonthecat.hom.deserializers;

import com.github.simonthecat.hom.DeserializationException;
import com.github.simonthecat.hom.TypeDeserializer;
import org.apache.hadoop.hbase.util.Bytes;

public class IntegerDeserializer implements TypeDeserializer<Integer> {
    @Override
    public Integer deserialize(byte[] bytes) throws DeserializationException {
        return Bytes.toInt(bytes);
    }
}
