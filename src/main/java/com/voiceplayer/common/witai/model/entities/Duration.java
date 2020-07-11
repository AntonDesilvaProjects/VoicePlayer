package com.voiceplayer.common.witai.model.entities;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.util.HashMap;
import java.util.Map;

public class Duration extends Entity {
    public static class Normalization {
        private String unit;
        private double value;
        public String getUnit() {
            return unit;
        }

        public Normalization setUnit(String unit) {
            this.unit = unit;
            return this;
        }

        public double getValue() {
            return value;
        }

        public Normalization setValue(double value) {
            this.value = value;
            return this;
        }
    }
    private String unit;
    private Normalization normalized;
    private Map<String, Object> additionalValues = new HashMap<>();

    @JsonAnySetter
    public void setAdditionalValues(String property, Object value) {
        additionalValues.put(property, value);
    }
    public String getUnit() {
        return unit;
    }

    public Duration setUnit(String unit) {
        this.unit = unit;
        return this;
    }

    public Normalization getNormalized() {
        return normalized;
    }

    public Duration setNormalized(Normalization normalized) {
        this.normalized = normalized;
        return this;
    }

    public Map<String, Object> getAdditionalValues() {
        return additionalValues;
    }

    public Duration setAdditionalValues(Map<String, Object> additionalValues) {
        this.additionalValues = additionalValues;
        return this;
    }

    @Override
    public String getStandardEntityName() {
        return "wit/duration";
    }

}
