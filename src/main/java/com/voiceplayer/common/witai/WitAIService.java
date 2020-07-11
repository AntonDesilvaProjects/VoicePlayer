package com.voiceplayer.common.witai;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.collect.ImmutableMap;
import com.voiceplayer.common.witai.model.entities.Contact;
import com.voiceplayer.common.witai.model.entities.Duration;
import com.voiceplayer.common.witai.model.entities.Entity;
import com.voiceplayer.common.witai.model.IntentResolutionResponse;
import com.voiceplayer.common.witai.model.entities.SearchQuery;
import com.voiceplayer.utils.ApplicationUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Service
public class WitAIService {
    private final Properties credentials;
    private final RestTemplate restTemplate;
    private final String WIT_AI_ENDPOINT;
    private final String WIT_AI_VERSION;

    public static final Map<String, Class> STANDARD_ENTITY_TO_MODEL_CLASS_MAP = ImmutableMap.of(
            "wit$contact", Contact.class,
            "wit$duration", Duration.class,
            "wit$number", Number.class,
            "wit$search_query", SearchQuery.class);

    public WitAIService(@Qualifier("credentials") Properties credentials, RestTemplate restTemplate,
                        @Value("${witai.api.endpoint}")String witAiEndpoint,
                        @Value("${witai.api.version}")String version) {
        this.credentials = credentials;
        this.restTemplate = restTemplate;
        this.WIT_AI_ENDPOINT = witAiEndpoint;
        this.WIT_AI_VERSION = version;
    }

    public IntentResolutionResponse resolveIntent(String text) {
        final HttpHeaders headers = ApplicationUtils.buildHeaders("Authorization", "Bearer " + credentials.getProperty("witai.accessToken"));
        final String apiEndpoint = WIT_AI_ENDPOINT + "/message";
        final String url = UriComponentsBuilder
                .fromHttpUrl(apiEndpoint)
                .queryParam("v", WIT_AI_VERSION)
                .queryParam("q", text)
                .build()
                .toUriString();
        final ResponseEntity<IntentResolutionResponse> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), IntentResolutionResponse.class);

        return response.getBody();
    }

    /**
     * Given a JsonNode consisting of entities and entity-role names, extract the entities.
     * @param entities JsonNode consisting of entities from Wit AI
     * @param entityRoleName entity-role name. For example "wit$duration:duration" extracts the standard entity
     *                    duration whose role is to specify some duration
     * @param entityClass class to bind the entity. Must be the entity class or one of its subtypes
     * */
    public <T extends Entity> List<T> extractEntities(JsonNode entities, String entityRoleName, Class<T> entityClass) {
        final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        final JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, entityClass);
        final ObjectReader reader = mapper.readerFor(type);
        ArrayNode node = (ArrayNode) entities.get(entityRoleName);
        List<T> entityList = null;
        if (node != null) {
            try {
                entityList = reader.readValue(node);
            } catch (IOException e) {
                throw new IllegalArgumentException("Unable to parse the entity!", e);
            }
        }
        return entityList;
    }
}
