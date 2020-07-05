package com.voiceplayer.common.witai.model.entities;

/**
 *  This is the base class for any type of WitAI entity object. Since almost all entities have their
 *  own set of fields, this class contains a common set of fields and mainly intended for inheritance
 *  purposes.
 *
 *  Other classes within this package support the standard Entities that come with WitAI
 * */
public class Entity {
    private String id;
    private String fullName;
    private String name;
    private String role;
    private int start;
    private int end;
    private String body;
    private double confidence;
    private String value;
    private String type;

    public String getId() {
        return id;
    }

    public Entity setId(String id) {
        this.id = id;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public Entity setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getName() {
        return name;
    }

    public Entity setName(String name) {
        this.name = name;
        return this;
    }

    public String getRole() {
        return role;
    }

    public Entity setRole(String role) {
        this.role = role;
        return this;
    }

    public int getStart() {
        return start;
    }

    public Entity setStart(int start) {
        this.start = start;
        return this;
    }

    public int getEnd() {
        return end;
    }

    public Entity setEnd(int end) {
        this.end = end;
        return this;
    }

    public String getBody() {
        return body;
    }

    public Entity setBody(String body) {
        this.body = body;
        return this;
    }

    public double getConfidence() {
        return confidence;
    }

    public Entity setConfidence(double confidence) {
        this.confidence = confidence;
        return this;
    }

    public String getValue() {
        return value;
    }

    public Entity setValue(String value) {
        this.value = value;
        return this;
    }

    public String getType() {
        return type;
    }

    public Entity setType(String type) {
        this.type = type;
        return this;
    }
}
