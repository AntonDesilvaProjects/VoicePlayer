package com.voiceplayer.intent;

import com.voiceplayer.model.IntentActionRequest;
import org.springframework.util.CollectionUtils;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractIntentHandler implements IntentHandler {
    public Set<String> getMissingRequiredEntities(final IntentActionRequest request) {
        return request.getIntentEntityMapping()
                .getRequiredEntityNames()
                .stream()
                .filter(name -> CollectionUtils.isEmpty(request.getIntentResolutionResponse().getEntitiesByNameAndRole(name)))
                .collect(Collectors.toSet());
    }
}
