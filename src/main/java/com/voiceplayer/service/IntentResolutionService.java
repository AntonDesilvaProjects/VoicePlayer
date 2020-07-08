package com.voiceplayer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.voiceplayer.model.IntentEntityMapping;
import com.voiceplayer.utils.ApplicationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class IntentResolutionService {
    private final Map<String, IntentEntityMapping> intentEntityMappings;

    public IntentResolutionService(@Value("${intent.config}") String intentConfigPath) {
        this.intentEntityMappings = getIntentEntityConfig(intentConfigPath);
    }

    private Map<String, IntentEntityMapping> getIntentEntityConfig(String configPath) {
        final String rawConfig = ApplicationUtils.getFileContent(configPath);
        final ObjectMapper mapper = new ObjectMapper();
        List<IntentEntityMapping> mappings;
        try {
           mappings = mapper.readValue(rawConfig, new TypeReference<List<IntentEntityMapping>>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new IllegalStateException("Unable to load intent entity configuration!", e);
        }
        return mappings.stream().collect(Collectors.toMap(IntentEntityMapping::getIntentName, Function.identity()));
    }
    // some text => intent resolution response => for given intent
    // we need to define a set of entities that are optional and required
    // if none of the required entities are present, then system didn't understand
    // the user's request
    // presence of optional entities indicate that the system can do additional work
}
