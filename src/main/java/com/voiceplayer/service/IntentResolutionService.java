package com.voiceplayer.service;

import com.voiceplayer.common.witai.WitAIService;
import com.voiceplayer.common.witai.model.IntentResolutionResponse;
import org.springframework.stereotype.Service;

@Service
public class IntentResolutionService {

    final WitAIService witAIService;

    public IntentResolutionService(WitAIService witAIService) {
        this.witAIService = witAIService;
    }

    // some text => intent resolution response => for given intent
    // we need to define a set of entities that are optional and required
    // if none of the required entities are present, then system didn't understand
    // the user's request
    // presence of optional entities indicate that the system can do additional work

    public IntentResolutionResponse resolveIntent(final String text) {
        return witAIService.resolveIntent(text);
    }
}
