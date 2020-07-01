package com.voiceplayer.domain;

import java.util.ArrayList;
import java.util.List;

public class AudioFileSearchParams {
    public static class Operator {
        private final AudioFileSearchBuilder builder;
        private final String operand1;
        public Operator(String operand1, AudioFileSearchBuilder builder) {
            this.builder = builder;
            this.operand1 = operand1;
        }
        public AudioFileSearchBuilder contains(String operand2) {
            builder.queryStrings.add(operand1 + " contains " + operand2);
            return builder;
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
    }
}
