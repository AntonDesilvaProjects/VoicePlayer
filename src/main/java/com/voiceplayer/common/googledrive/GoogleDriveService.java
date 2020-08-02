package com.voiceplayer.common.googledrive;

import com.voiceplayer.common.googledrive.model.*;
import com.voiceplayer.utils.ApplicationUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

/**
 *  Service implementation responsible for interfacing with Google Drive to access users audio
 *  files
 * */
@Repository
public class GoogleDriveService {

    private final Properties credentials;
    private final RestTemplate restTemplate;
    private final String GOOGLE_DRIVE_V3_ENDPOINT;
    private final String GOOGLE_OAUTH2_ENDPOINT;

    private final String FILES_ENDPOINT;

    public GoogleDriveService(RestTemplate restTemplate,
                              @Qualifier("credentials") Properties credentials,
                              @Value("${google.drive.api.endpoint}") String googleDriveAPIEndpoint,
                              @Value("${google.oauth2-endpoint}") String googleOAuth2Endpoint) {
        this.restTemplate = restTemplate;
        this.credentials = credentials;
        this.GOOGLE_DRIVE_V3_ENDPOINT = googleDriveAPIEndpoint;
        this.GOOGLE_OAUTH2_ENDPOINT = googleOAuth2Endpoint;

        this.FILES_ENDPOINT = this.GOOGLE_DRIVE_V3_ENDPOINT + "/files";
    }

    public File get(String id) {
        throw new NotImplementedException();
    }

    public ListResponse list(final SearchParams params) {
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

    public InputStreamResource downloadFile(final String fileId) {
        // need to fetch the file metadata first
        // make a decision if the file is google type or other
        final String urlString = UriComponentsBuilder
                .fromHttpUrl(FILES_ENDPOINT)
                .path("/" + fileId)
                .queryParam("alt", "media")
                .build().toUriString();
        InputStreamResource resource = null;
        try {
            final URL url = new URL(urlString);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Bearer " + getAccessToken());
            resource = new InputStreamResource(urlConnection.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resource;
    }

    private FileListResponse listFiles(SearchParams params) {
        final HttpHeaders headers = ApplicationUtils.buildHeaders("Authorization", "Bearer " + getAccessToken());
        final UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromHttpUrl(FILES_ENDPOINT);
        // add query params
        if (StringUtils.isEmpty(params.getQuery())) {
            throw new IllegalArgumentException("Query must be specified");
        }
        uriBuilder.queryParam("q", params.getQuery());
        if (StringUtils.isNotEmpty(params.getFieldsQuery())) {
            uriBuilder.queryParam("fields", params.getFieldsQuery());
        }

        final String url = uriBuilder.build().toUriString();
        ResponseEntity<FileListResponse> response = restTemplate.exchange(url, HttpMethod.GET,
                new HttpEntity<>(headers), FileListResponse.class);

        return response.getBody();
    }

    private String getAccessToken() {
        // use credentials to get an access token
        return getAccessToken(credentials.getProperty("google.clientId"),
                credentials.getProperty("google.clientSecret"),
                credentials.getProperty("google.refreshToken")).getAccessToken();
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
