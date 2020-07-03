package com.voiceplayer.common.googledrive;

import com.voiceplayer.common.googledrive.model.*;
import com.voiceplayer.utils.ApplicationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Properties;

/**
 *  DAO implementation responsible for interfacing with Google Drive to access users audio
 *  files
 * */
@Repository
public class GoogleDriveService {

    private final RestTemplate restTemplate;
    private final String GOOGLE_DRIVE_V3_ENDPOINT;
    private final String GOOGLE_OAUTH2_ENDPOINT;
    private final String CREDENTIALS_PATH;

    private Properties credentials = null;

    public GoogleDriveService(RestTemplate restTemplate,
                              @Value("${google.drive.endpoint}") String googleDriveAPIEndpoint,
                              @Value("${google.oauth2-endpoint}") String googleOAuth2Endpoint,
                              @Value("${google.credential-config-path}") String googleCredentialConfigPath) {
        this.restTemplate = restTemplate;
        this.GOOGLE_DRIVE_V3_ENDPOINT = googleDriveAPIEndpoint;
        this.GOOGLE_OAUTH2_ENDPOINT = googleOAuth2Endpoint;
        this.CREDENTIALS_PATH = googleCredentialConfigPath;
    }

    public File get(String id) {
        return null;
    }

    public ListResponse list(SearchParams params) {
        if (params == null || params.getEntityType() == null) {
            throw new IllegalArgumentException("Entity type required for listing entities!");
        }
        ListResponse response = null;
        switch (params.getEntityType()) {
            case FILE:
                response = listFiles(params);
                break;
            default:
                throw new UnsupportedOperationException("Entity type not supported at this time");
        }
        // build the search request
        return response;
    }

    private FileListResponse listFiles(SearchParams params) {
        final HttpHeaders headers = buildHeaders("Authorization", "Bearer " + getAccessToken());
        final String apiEndpoint = GOOGLE_DRIVE_V3_ENDPOINT + "/files";

        final String url = UriComponentsBuilder
                .fromHttpUrl(apiEndpoint)
                .queryParam("q", params.getQuery())
                .build()
                .toUriString();

        ResponseEntity<FileListResponse> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), FileListResponse.class);

        return response.getBody();
    }

    private HttpHeaders buildHeaders(String... headers) {
        if (headers.length == 0 || headers.length % 2 != 0) {
            throw new IllegalArgumentException("Invalid headers!");
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        for (int i = 0; i < headers.length; i = i + 2) {
            httpHeaders.add(headers[i], headers[i+1]);
        }
        return httpHeaders;
    }

    private String getAccessToken() {
        // load and cache credentials if not already done
        if (credentials == null) {
            credentials = ApplicationUtils.loadPropertiesFile(CREDENTIALS_PATH);
        }
        // use credentials to get an access token
        return getAccessToken(credentials.getProperty("clientId"), credentials.getProperty("clientSecret"), credentials.getProperty("refreshToken")).getAccessToken();
    }

    public Token getAccessToken(final String clientId, final String clientSecret, final String refreshToken) {
        final String refreshTokenEndpoint = GOOGLE_OAUTH2_ENDPOINT + "/token";
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("refresh_token", refreshToken);
        formData.add("grant_type", "refresh_token");

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        final HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);
        final ResponseEntity<Token> response = restTemplate.exchange(refreshTokenEndpoint, HttpMethod.POST, request, Token.class);
        return response.getBody();
    }
}
