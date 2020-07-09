package com.voiceplayer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.voiceplayer.common.witai.model.IntentResolutionResponse;
import com.voiceplayer.intent.IntentHandler;
import com.voiceplayer.model.IntentEntityMapping;
import com.voiceplayer.utils.ApplicationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class IntentHandlerService {
    private final Map<String, IntentEntityMapping> intentEntityMappings;
    private final Map<String, IntentHandler> intentHandlers;

    public IntentHandlerService(@Value("${intent.config}") String intentConfigPath,
                                   Map<String, IntentHandler> intentHandlers) {
        this.intentEntityMappings = getIntentEntityConfig(intentConfigPath);
        this.intentHandlers = intentHandlers;
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

    public void handleIntent(IntentResolutionResponse intentResolutionResponse) {
        // find the intent
        // invoke the method with appropriate metadata
    }
}
