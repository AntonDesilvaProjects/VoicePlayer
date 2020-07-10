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

    /**
     *  VoiceRequest => Intent Resolution[WIT service] => Service/DAO layers => VoiceResponse
     *
     *  User will only ever have one intention: Play a song that is found on the drive
     *  We need to elucidate:
     *      1. The audio file name
     *      2. The artist if available
     *      3. frequency - how many times to play the song?
     *      4. fragment - from which point in audio file to which
     *      5. Speed - how fast or slow ?
     *
     *   Ex. Can you play the [flamenco backing track] at half-speed ?
     *      -> play_music
     *      -> flamenco backing track
     *      -> half-speed [metadata]
     *
     *      Find exactly one match - play the song
     *      Find no matches - "no music found"
     *      Find more than one match -
     *          n < 5 - dictate the list to the user
     *          n >= 5 - "too many results. Please try again"
     * */
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
        return witAIService.resolveIntent("Play rain by Jesse Cooke");
    }

}
