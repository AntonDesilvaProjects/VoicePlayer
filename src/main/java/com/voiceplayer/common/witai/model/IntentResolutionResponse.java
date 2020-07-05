package com.voiceplayer.common.witai.model;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public class IntentResolutionResponse {
    private String text;
    private List<Intent> intents;
    private JsonNode entities;
    private JsonNode traits;

    public String getText() {
        return text;
    }

    public IntentResolutionResponse setText(String text) {
        this.text = text;
        return this;
    }

    public List<Intent> getIntents() {
        return intents;
    }

    public IntentResolutionResponse setIntents(List<Intent> intents) {
        this.intents = intents;
        return this;
    }

    public JsonNode getEntities() {
        return entities;
    }

    public IntentResolutionResponse setEntities(JsonNode entities) {
        this.entities = entities;
        return this;
    }

    public JsonNode getTraits() {
        return traits;
    }

    public IntentResolutionResponse setTraits(JsonNode traits) {
        this.traits = traits;
        return this;
    }
}
