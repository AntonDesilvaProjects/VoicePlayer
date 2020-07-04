package com.voiceplayer.common.witai.model;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public class TextResponse {
    private String text;
    private List<Intent> intents;
    // structure of the following two object vary greatly so make then
    // JsonNode
    // TODO: Off utility methods to check fo existence of particular entities triats
    private JsonNode entities;
    private JsonNode traits;
}
