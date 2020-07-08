package com.voiceplayer.model;

import java.util.Set;

public class IntentEntityMapping {
    private String intentName;
    private Set<String> requiredEntityNames;
    private Set<String> optionalEntityNames;

    public String getIntentName() {
        return intentName;
    }

    public void setIntentName(String intentName) {
        this.intentName = intentName;
    }

    public Set<String> getRequiredEntityNames() {
        return requiredEntityNames;
    }

    public void setRequiredEntityNames(Set<String> requiredEntityNames) {
        this.requiredEntityNames = requiredEntityNames;
    }

    public Set<String> getOptionalEntityNames() {
        return optionalEntityNames;
    }

    public void setOptionalEntityNames(Set<String> optionalEntityNames) {
        this.optionalEntityNames = optionalEntityNames;
    }
}
