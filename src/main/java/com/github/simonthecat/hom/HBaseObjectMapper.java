package com.github.simonthecat.hom;

import com.google.common.base.Throwables;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.hbase.client.Result;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;

public class HBaseObjectMapper {

    private TypeDeserializerRegistry typeDeserializerRegistry;

    private PojoMappingRegistry pojoMappingRegistry = new PojoMappingRegistry();

    HBaseObjectMapper(TypeDeserializerRegistry typeDeserializerRegistry) {
        this.typeDeserializerRegistry = typeDeserializerRegistry;
    }

    @SuppressWarnings("unchecked")
    public <T> T map(Result result, Class<T> targetClass) {
        Set<FieldMapping> mappings = pojoMappingRegistry.get(targetClass);
        try {
            T mappedObject = targetClass.newInstance();
            Map propertyMap = new HashMap();

            for (FieldMapping mapping : mappings) {
                byte[] bytes = mapping.getValueExtractor().extract(result);
                TypeDeserializer<?> deserializer = typeDeserializerRegistry.getDeserializer(mapping.getFieldType());
                Object value = deserializer.deserialize(bytes);
                propertyMap.put(mapping.getFieldName(), value);
            }

            BeanUtils.populate(mappedObject, propertyMap);
            return mappedObject;
        } catch (InstantiationException e) {
            throw new RuntimeException("Couldn't create new instance of specified class", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Target class does not have a no-arg public constructor", e);
        } catch (InvocationTargetException e) {
            throw Throwables.propagate(e);
        }
    }

}
