package com.voiceplayer.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * https://developers.google.com/drive/api/v3/ref-search-terms
 * https://developers.google.com/drive/api/v3/search-files
 * */
public class AudioFileSearchParams {

    public static class Operator {
        private final AudioFileSearchBuilder builder;
        private final String operand1;
        private final StringBuilder queryBuilder;
        public Operator(String operand1, AudioFileSearchBuilder builder) {
            this.builder = builder;
            this.operand1 = operand1;
            this.queryBuilder = new StringBuilder(operand1);
        }
        public AudioFileSearchBuilder done() {
            builder.queryStrings.add(queryBuilder.toString());
            return builder;
        }
        public Operator contains(String... ORedOperands) {
            for (String operand : ORedOperands) {
                queryBuilder.append(operand1);
                queryBuilder.append(" contains ");
                queryBuilder.append(operand);
                queryBuilder.append(" OR ");
            }
            return this;
        }
        public Operator and(String... operands) {
            for (String operand : operands) {
                queryBuilder.append(operand1);
                queryBuilder.append(" contains ");
                queryBuilder.append(operand);
                queryBuilder.append(" AND ");
            }
            return this;
        }
    }

    public static class AudioFileSearchBuilder {
        private String query;
        private List<String> queryStrings = new ArrayList<>();
        public AudioFileSearchBuilder withQuery(String query) {
            this.query = query;
            return this;
        }
        public Operator with(String fileName) {
            return new Operator(fileName, this);
        }
        public AudioFileSearchParams build() {
            return new AudioFileSearchParams();
        }
    }

    public static void main(String... args) {
        AudioFileSearchParams params = new AudioFileSearchBuilder()
                .with("file").contains("anton").and("peprika").done()
                .withQuery("")
                .build();
    }
}
