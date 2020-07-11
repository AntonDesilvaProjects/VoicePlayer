package com.voiceplayer.common.witai.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.voiceplayer.common.witai.model.entities.Entity;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class IntentResolutionResponse {
    private String text;
    private List<Intent> intents;
    private JsonNode entities;
    private JsonNode traits;

    private List<Entity> entityList;
    private Map<String, List<Entity>> entityRoleToEntitiesMap;

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

    public List<Entity> getEntityList() {
        return entityList;
    }

    public IntentResolutionResponse setEntityList(List<Entity> entityList) {
        this.entityList = entityList;
        if (CollectionUtils.isNotEmpty(entityList)) {
            this.entityRoleToEntitiesMap = entityList.stream()
                    .collect(Collectors.groupingBy(Entity::getFullName, Collectors.toList()));
        }
        return this;
    }

    public List<Entity> getEntitiesByNameAndRole(String entityNameRole) {
        return new ArrayList<>(entityRoleToEntitiesMap.get(entityNameRole));
    }
}
