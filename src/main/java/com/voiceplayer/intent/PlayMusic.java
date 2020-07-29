package com.voiceplayer.intent;

import com.voiceplayer.VoicePlayerConstants;
import com.voiceplayer.common.witai.model.IntentResolutionResponse;
import com.voiceplayer.common.witai.model.entities.Entity;
import com.voiceplayer.common.witai.model.entities.SearchQuery;
import com.voiceplayer.exception.IntentException;
import com.voiceplayer.model.*;
import com.voiceplayer.service.AudioPlayerService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.BiFunction;
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
public class PlayMusic extends AbstractIntentHandler<PlayMusicResponse> {
    private static final String AUDIO_SQ_ENTITY_NAME = "wit$search_query:audio";
    private static final String ARTIST_SQ_ENTITY_NAME = "wit$search_query:artist";
    private static final String ARTIST_CONTACT_ENTITY_NAME = "wit$contact:contact";
    private static final String SPEED_ENTITY_NAME = "speed:speed";
    private static final String SPEED_SQ_ENTITY_NAME = "wit$search_query:free_text_speed";

    private final AudioPlayerService audioPlayerService;

    private final BiFunction<IntentResolutionResponse, String, Entity> GET_FIRST_ENTITY = (intentResolution, name) ->
            Optional.ofNullable(intentResolution.getEntitiesByNameAndRole(name))
                    .orElse(new ArrayList<>())
                    .stream()
                    .findFirst()
                    .orElse(null);

    public PlayMusic(AudioPlayerService audioPlayerService) {
        this.audioPlayerService = audioPlayerService;
    }

    @Override
    public IntentActionResponse<PlayMusicResponse> handleIntent(IntentActionRequest intentActionRequest) {
        IntentActionResponse<PlayMusicResponse> response = new IntentActionResponse<>();
        try {
            Set<String> missingEntities = getMissingRequiredEntities(intentActionRequest);
            if (CollectionUtils.isNotEmpty(missingEntities)) {
                throw new IntentException("Please specify the name of audio file to play");
            }
            final IntentResolutionResponse intentResolution = intentActionRequest.getIntentResolutionResponse();

            // we will get the first for each type of entity
            final SearchQuery audioFileEntity = (SearchQuery) GET_FIRST_ENTITY.apply(intentResolution, AUDIO_SQ_ENTITY_NAME);
            final Entity contactEntity = GET_FIRST_ENTITY.apply(intentResolution, ARTIST_CONTACT_ENTITY_NAME);
            final Entity artistEntity = GET_FIRST_ENTITY.apply(intentResolution, ARTIST_SQ_ENTITY_NAME);

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
                throw new IntentException(String.format("Found the following tracks: %s. Please repeat your selection.",
                        searchResults.stream().map(AudioFile::getName).collect(Collectors.joining(","))));
            }

            // get any additional metadata
            // for speed we have two possible entities
            final SearchQuery speedSQEntity = (SearchQuery) GET_FIRST_ENTITY.apply(intentResolution, SPEED_SQ_ENTITY_NAME);
            final Entity speedEntity = GET_FIRST_ENTITY.apply(intentResolution, SPEED_ENTITY_NAME);
            final String speed = StringUtils.isEmpty(speedEntity.getValue()) ? speedSQEntity.getValue() : speedEntity.getValue();

            response.setSuccessful(true);
            response.setResponse(new PlayMusicResponse()
                    .setAudioFile(searchResults.get(0))
                    .setSpeed(resolveAudioSpeed(speed)));
        } catch (IntentException i) {
            response.setSuccessful(false);
            response.setResponseText(i.getMessage());
        }
        return response;
    }

    /**
     *  Map verbal speed phrase to a number
     * */
    private double resolveAudioSpeed(String speedString) {
        // if no speed is provided, play at normal speed
        if (StringUtils.isEmpty(speedString)) {
            return 1.0;
        }

        double speed = 1.0;
        for (String token: speedString.split(" ")) {
            final String tokenLower = token.toLowerCase();
            // handle percentages
            if (tokenLower.contains("%")) {
                String percentage = token.substring(0, token.length() -1);
                if (StringUtils.isNumeric(percentage)) {
                    speed = Double.parseDouble(percentage) / 100;
                    break;
                }
            } else {
                // handle numerical phrases
                speed = Optional.ofNullable(VoicePlayerConstants.SPEED_PHRASE_TO_NUMERICAL_VALUE.get(tokenLower)).orElse(speed);
            }
        }
        return speed;
    }
}
