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
        final IntentResolutionResponse intent = intentResolutionService.resolveIntent(request.getRequestText());
        IntentActionResponse actionResponse;
        // attempts to action the user intent
        try {
            actionResponse = intentHandlerService.handle(intent);
        } catch (IntentException v) {
            // any exception thrown should be caught and converted to appropriate conversational
            // responses that can be voiced
            actionResponse = handleException(v);
        }
        // build a Voice response from the action resposne
        return new VoiceResponse<IntentActionResponse>().setResult(actionResponse);
    }

    private IntentActionResponse handleException(IntentException exception) {
        // map the exception to IntentActionResponse
        return null;
    }
}
