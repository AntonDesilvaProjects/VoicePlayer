package com.voiceplayer.intent;

import com.voiceplayer.model.IntentActionRequest;
import com.voiceplayer.model.IntentActionResponse;
import org.springframework.stereotype.Component;

@Component("play_music")
public class PlayMusic implements IntentHandler {
    @Override
    public IntentActionResponse handleIntent(IntentActionRequest intentActionRequest) {
        return null;
    }
}
