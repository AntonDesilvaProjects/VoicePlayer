package com.voiceplayer.intent;

import com.voiceplayer.model.IntentActionRequest;
import com.voiceplayer.model.IntentActionResponse;
import com.voiceplayer.model.IntentEntityMapping;
import org.springframework.stereotype.Component;

@Component("play_music")
public class PlayMusic implements IntentHandler {
    @Override
    public IntentActionResponse handleIntent(IntentActionRequest intentActionRequest) {
        return null;
    }


    private void validateIntentActionRequest(final IntentEntityMapping mapping ) {
        // validate that given intent has all the required entities
        // request.getIntentResolutionResponse().get request.getIntentEntityMapping().getRequiredEntityNames()
    }
}
