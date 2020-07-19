package com.voiceplayer.intent;

import com.voiceplayer.common.witai.model.IntentResolutionResponse;
import com.voiceplayer.common.witai.model.entities.Entity;
import com.voiceplayer.common.witai.model.entities.SearchQuery;
import com.voiceplayer.exception.IntentException;
import com.voiceplayer.model.AudioFile;
import com.voiceplayer.model.AudioFileSearchParams;
import com.voiceplayer.model.IntentActionRequest;
import com.voiceplayer.model.IntentActionResponse;
import com.voiceplayer.service.AudioPlayerService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private static final String ARTIST_SQ_ENTITY_NAME = "wit$search_query:artist";
    private static final String ARTIST_CONTACT_ENTITY_NAME = "wit$contact:contact";

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

            // we will get the first for each type of entity
            final SearchQuery audioFileEntity = (SearchQuery) intentResolution.getEntitiesByNameAndRole(AUDIO_SQ_ENTITY_NAME).get(0);
            final Entity contactEntity = intentResolution.getEntitiesByNameAndRole(ARTIST_CONTACT_ENTITY_NAME).get(0);
            final Entity artistEntity = intentResolution.getEntitiesByNameAndRole(ARTIST_SQ_ENTITY_NAME).get(0);

            // find file
            List<AudioFile> searchResults = audioPlayerService.search(new AudioFileSearchParams()
                    .setFileName(audioFileEntity.getValue())
                    .setArtists(Stream.of(contactEntity, artistEntity)
                            .filter(Objects::nonNull)
                            .map(Entity::getValue)
                            .collect(Collectors.toSet())));

            if (CollectionUtils.isEmpty(searchResults)) {
                throw new IntentException("Unable to find the requested track. Please try again.");
            } else if (searchResults.size() > 5) {
                throw new IntentException(String.format("Found %s results. Please try to be more specific.", searchResults.size()));
            } else if (searchResults.size() > 1) {
                // recite the audio file list to the user
                final String message = "Found the following tracks:" + searchResults.stream().map(AudioFile::getName).collect(Collectors.joining(",")) + ". " +
                        "Please repeat your selection";
                throw new IntentException(message);
            }

            // we have exactly one track - download it and provide a link for the
            // client to download and play it
            // add other meta data to the response

        } catch (IntentException i) {
            response.setResponseText(i.getMessage());
        }
        return response;
    }
}
