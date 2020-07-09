package com.voiceplayer.intent;

import com.voiceplayer.model.IntentActionRequest;
import com.voiceplayer.model.IntentActionResponse;

@FunctionalInterface
public interface IntentHandler {
    IntentActionResponse handleIntent(IntentActionRequest intentActionRequest);
}
