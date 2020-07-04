package com.voiceplayer.common.witai.model;

public class Intent {
    private String id;
    private String name;
    private double confidence;

    public String getId() {
        return id;
    }

    public Intent setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Intent setName(String name) {
        this.name = name;
        return this;
    }

    public double getConfidence() {
        return confidence;
    }

    public Intent setConfidence(double confidence) {
        this.confidence = confidence;
        return this;
    }
}
