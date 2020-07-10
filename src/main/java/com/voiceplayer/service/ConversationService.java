package com.voiceplayer.service;

import com.voiceplayer.common.witai.model.IntentResolutionResponse;
import com.voiceplayer.exception.IntentException;
import com.voiceplayer.model.IntentActionResponse;
import com.voiceplayer.model.VoiceRequest;
import com.voiceplayer.model.VoiceResponse;
import org.springframework.stereotype.Service;

@Service
public class ConversationService {
    private final IntentHandlerService intentHandlerService;
    private final IntentResolutionService intentResolutionService;

    public ConversationService(IntentResolutionService intentResolutionService,
                               IntentHandlerService intentHandlerService) {
        this.intentHandlerService = intentHandlerService;
        this.intentResolutionService = intentResolutionService;
    }

    public VoiceResponse handle(VoiceRequest request) {
        final IntentResolutionResponse intentResolutionResponse = intentResolutionService.resolveIntent(request.toString());
        IntentActionResponse actionResponse;
        try {
            actionResponse = intentHandlerService.handleIntent(intentResolutionResponse);
        } catch (IntentException v) {
            actionResponse = handleException(v);
        }
        return null;
    }

    private IntentActionResponse handleException(IntentException exception) {
        // map the exception to IntentActionResponse
        return null;
    }
}
