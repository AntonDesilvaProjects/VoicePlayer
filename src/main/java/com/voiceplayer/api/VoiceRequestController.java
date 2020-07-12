package com.voiceplayer.api;

import com.voiceplayer.common.witai.WitAIService;
import com.voiceplayer.common.witai.model.IntentResolutionResponse;
import com.voiceplayer.model.VoiceRequest;
import com.voiceplayer.model.VoiceResponse;
import com.voiceplayer.service.ConversationService;
import com.voiceplayer.service.IntentResolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/voice")
public class VoiceRequestController {

    private final ConversationService conversationService;

    public VoiceRequestController(@Autowired ConversationService conversationService) {
        this.conversationService = conversationService;
    }


    @PostMapping
    public VoiceResponse executeVoiceCommand(@RequestBody VoiceRequest request) {
        return conversationService.handle(request);
    }

    @Autowired
    WitAIService witAIService;

    @Autowired
    IntentResolutionService intentResolutionService;

    @GetMapping
    public IntentResolutionResponse test() {
        return witAIService.resolveIntent("Play rain by Jesse Cooke at half speed");
    }

}
