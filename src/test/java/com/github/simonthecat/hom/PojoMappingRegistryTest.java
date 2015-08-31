package com.github.simonthecat.hom;

import com.github.simonthecat.hom.model.CellDef;
import com.github.simonthecat.hom.model.RowKey;
import lombok.Data;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class PojoMappingRegistryTest {

    @Data
    class PojoWithCellDefs {

        @CellDef(family = "f1", qualifier = "test")
        private String strField;

        @CellDef(family = "f1", qualifier = "longfield")
        private Long longField;

        private String shouldBeSkipped;
    }

    @Data
    class PojoWithRowKey {

        @RowKey
        private String rowKey;

    }

    @Test
    public void testCorrectlyScansAllFieldsAnnotatedWithCellDefAnnotation() throws Exception {
        // given
        PojoMappingRegistry registry = new PojoMappingRegistry();

        // when
        Set<FieldMapping> mappings = registry.get(PojoWithCellDefs.class);

        // then
        List<FieldMapping> expectedElements = asList(
                new FieldMapping("strField", String.class, new ResultValueExtractor.ResultCellExtractor("f1", "test")),
                new FieldMapping("longField", Long.class, new ResultValueExtractor.ResultCellExtractor("f1", "longfield"))
        );
        assertThat(mappings).containsOnlyElementsOf(expectedElements);
    }

    @Test
    public void testCorrectlyScansForRowKeyAnnotation() throws Exception {
        // given
        PojoMappingRegistry registry = new PojoMappingRegistry();

        // when
        Set<FieldMapping> mappings = registry.get(PojoWithRowKey.class);

        // then
        assertThat(mappings).containsOnly(new FieldMapping("rowKey", String.class, ResultValueExtractor.RowKeyExtractor.INSTANCE));
    }
}