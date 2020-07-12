package com.voiceplayer.intent;

import com.voiceplayer.common.googledrive.model.FileListResponse;
import com.voiceplayer.common.googledrive.model.SearchParams;
import com.voiceplayer.common.witai.model.IntentResolutionResponse;
import com.voiceplayer.common.witai.model.entities.Entity;
import com.voiceplayer.common.witai.model.entities.SearchQuery;
import com.voiceplayer.exception.IntentException;
import com.voiceplayer.model.IntentActionRequest;
import com.voiceplayer.model.IntentActionResponse;
import com.voiceplayer.service.AudioPlayerService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Set;

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
@Component("play_music")
public class PlayMusic extends AbstractIntentHandler implements IntentHandler {
    private static final String AUDIO_SQ_ENTITY_NAME = "wit$search_query:audio";
    private static final String ARTIST_CONTACT_ENTITY_NAME = "wit$contact:contact";
    private static final String ARTIST_SQ_ENTITY_NAME = "wit$search_query:";

    private final AudioPlayerService audioPlayerService;

    public PlayMusic(AudioPlayerService audioPlayerService) {
        this.audioPlayerService = audioPlayerService;
    }

    @Override
    public IntentActionResponse handleIntent(IntentActionRequest intentActionRequest) {
        IntentActionResponse response = new IntentActionResponse();
        try {
            Set<String> missingEntities = getMissingRequiredEntities(intentActionRequest);
            if (CollectionUtils.isNotEmpty(missingEntities)) {
                throw new IntentException("Please specify the name of audio file to play");
            }
            final IntentResolutionResponse intentResolution = intentActionRequest.getIntentResolutionResponse();

            final SearchQuery audioFileEntity = (SearchQuery) intentResolution.getEntitiesByNameAndRole(AUDIO_SQ_ENTITY_NAME).get(0);
            final Entity contactEntity = intentResolution.getEntitiesByNameAndRole(ARTIST_CONTACT_ENTITY_NAME).get(0);
            final Entity artistEntity = intentResolution.getEntitiesByNameAndRole(ARTIST_SQ_ENTITY_NAME).get(0);

            // build a search query for file name by artist
            // find file
            FileListResponse fileListResponse = audioPlayerService.search(new SearchParams
                    .Builder()
                    .withQuery("")
                    .build());
            
            // add other meta data to the response

        } catch (IntentException i) {
            response.setResponseText(i.getMessage());
        }
        return response;
    }
}
