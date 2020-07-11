package com.voiceplayer.common.witai;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.collect.ImmutableMap;
import com.voiceplayer.common.witai.model.entities.*;
import com.voiceplayer.common.witai.model.IntentResolutionResponse;
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
import java.lang.Number;
import java.util.*;

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
        ResponseEntity<IntentResolutionResponse> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), IntentResolutionResponse.class);
        final IntentResolutionResponse intentResolutionResponse = response.getBody();
        // parse the entities
        intentResolutionResponse.setEntityList(extractEntities(intentResolutionResponse.getEntities()));
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
                entityList.forEach(e -> e.setFullName(entityRoleName));
            } catch (IOException e) {
                throw new IllegalArgumentException("Unable to parse the entity!", e);
            }
        }
        return entityList;
    }

    /**
     * Given a JsonNode consisting of entities, extract the entities.
     * @param entitiesNode JsonNode consisting of entities from Wit AI
     * */
    public List<Entity> extractEntities(JsonNode entitiesNode) {
        Iterator<String> entityNames = entitiesNode.fieldNames();
        List<Entity> entityList = new ArrayList<>();
        while (entityNames.hasNext()) {
            // the name will be in the format wit?entity-name:role for standard entities
            // and custom-name:role for custom entities
            String entityNameRole = entityNames.next();
            String searchName = entityNameRole.substring(0, entityNameRole.indexOf(':')).replace("$", "/");
            Class clazz = Optional.ofNullable(StandardEntity
                    .findUsing(e -> e.getName().equals(searchName)))
                    .map(StandardEntity::getClazz)
                    .orElse(Entity.class);
            entityList.addAll(extractEntities(entitiesNode, entityNameRole, clazz));
        }
        return entityList;
    }
}
