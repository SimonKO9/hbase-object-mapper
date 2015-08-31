package com.github.simonthecat.hom;

import lombok.Value;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

@FunctionalInterface
public interface ResultValueExtractor {

    byte[] extract(Result result);

    @Value
    final class ResultCellExtractor implements ResultValueExtractor {
        private String family;
        private String qualifier;

        @Override
        public byte[] extract(Result result) {
            return result.getValue(Bytes.toBytes(family), Bytes.toBytes(qualifier));
        }
    }

    final class RowKeyExtractor implements ResultValueExtractor {
        private RowKeyExtractor() {
        }

        public final static RowKeyExtractor INSTANCE = new RowKeyExtractor();

        @Override
        public byte[] extract(Result result) {
            return result.getRow();
        }
    }

}