package com.voiceplayer.intent;

import com.voiceplayer.model.IntentActionRequest;
import com.voiceplayer.model.IntentActionResponse;

@FunctionalInterface
public interface IntentHandler<T> {
    IntentActionResponse<T> handleIntent(IntentActionRequest intentActionRequest);
}
