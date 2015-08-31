package com.github.simonthecat.hom;

import com.github.simonthecat.hom.model.CellDef;
import com.github.simonthecat.hom.model.RowKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.hadoop.hbase.client.Result;
import org.junit.Test;

import static org.apache.hadoop.hbase.util.Bytes.toBytes;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HBaseObjectMapperTest {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SimplePojo {

        @RowKey
        private String rowKey;

        @CellDef(family = "f1", qualifier = "q1")
        private String strValue;

        @CellDef(family = "f2", qualifier = "q2")
        private Long longValue;

        @CellDef(family = "f3", qualifier = "q3")
        private Float floatValue;

        @CellDef(family = "f4", qualifier = "q4")
        private Double doubleValue;

        @CellDef(family = "f5", qualifier = "q5")
        private Integer intValue;

    }


    @Test
    public void mapperShouldMapAllFieldsUsingDefaultDeserializers() throws Exception {
        // given
        HBaseObjectMapper mapper = HBaseObjectMapperBuilder.builder()
                .withDefaultDeserializers()
                .build();

        // when
        Result mockResult = mock(Result.class);
        when(mockResult.getRow()).thenReturn(toBytes("rowkey01"));
        when(mockResult.getValue(toBytes("f1"), toBytes("q1"))).thenReturn(toBytes("a string"));
        when(mockResult.getValue(toBytes("f2"), toBytes("q2"))).thenReturn(toBytes(32L));
        when(mockResult.getValue(toBytes("f3"), toBytes("q3"))).thenReturn(toBytes(3.16f));
        when(mockResult.getValue(toBytes("f4"), toBytes("q4"))).thenReturn(toBytes(1.0));
        when(mockResult.getValue(toBytes("f5"), toBytes("q5"))).thenReturn(toBytes(99));

        SimplePojo mapped = mapper.map(mockResult, SimplePojo.class);

        // then
        SimplePojo expected = new SimplePojo("rowkey01", "a string", 32L, 3.16f, 1.0, 99);
        assertThat(mapped).isEqualTo(expected);

    }
}