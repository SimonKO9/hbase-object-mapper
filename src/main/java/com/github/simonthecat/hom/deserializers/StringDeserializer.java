package com.github.simonthecat.hom.deserializers;

import com.github.simonthecat.hom.DeserializationException;
import com.github.simonthecat.hom.TypeDeserializer;
import org.apache.hadoop.hbase.util.Bytes;

public class StringDeserializer implements TypeDeserializer<String> {
    @Override
    public String deserialize(byte[] bytes) throws DeserializationException {
        return Bytes.toString(bytes);
    }
}
