package com.voiceplayer.api;

import com.voiceplayer.domain.VoiceRequest;
import com.voiceplayer.domain.VoiceResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/voice")
public class VoiceRequestController {

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
     *
     *
     * */
    @PostMapping
    public VoiceResponse executeVoiceCommand(@RequestBody VoiceRequest request) {
        return null;
    }

}
