package com.github.simonthecat.hom.deserializers;

import com.github.simonthecat.hom.DeserializationException;
import com.github.simonthecat.hom.TypeDeserializer;
import org.apache.hadoop.hbase.util.Bytes;

public class LongDeserializer implements TypeDeserializer<Long> {
    @Override
    public Long deserialize(byte[] bytes) throws DeserializationException {
        return Bytes.toLong(bytes);
    }
}
