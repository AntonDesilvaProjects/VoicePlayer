package com.voiceplayer.common.googledrive;

import com.voiceplayer.common.googledrive.model.*;
import com.voiceplayer.common.restclient.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

/**
 *  Service implementation responsible for interfacing with Google Drive to access users audio
 *  files
 * */
@Repository
public class GoogleDriveService {
    private final RestClient restClient;
    private final Properties credentials;
    private final String GOOGLE_DRIVE_V3_ENDPOINT;
    private final String GOOGLE_OAUTH2_ENDPOINT;

    private final String FILES_ENDPOINT;

    public GoogleDriveService(RestTemplate restTemplate,
                              @Qualifier("credentials") Properties credentials,
                              @Value("${google.drive.api.endpoint}") String googleDriveAPIEndpoint,
                              @Value("${google.oauth2-endpoint}") String googleOAuth2Endpoint) {
        this.restClient = new RestTemplateClient(restTemplate);
        this.credentials = credentials;
        this.GOOGLE_DRIVE_V3_ENDPOINT = googleDriveAPIEndpoint;
        this.GOOGLE_OAUTH2_ENDPOINT = googleOAuth2Endpoint;

        this.FILES_ENDPOINT = this.GOOGLE_DRIVE_V3_ENDPOINT + "/files";
    }

    public File get(String fileId) {
        return restClient.execute(new Request
                .Builder<Void, File>(FILES_ENDPOINT + "/" + fileId, HttpMethod.GET, File.class)
                .errorHandler(error -> {
                    if (!HttpStatus.NOT_FOUND.equals(error.getHttpStatus())) {
                        throw (RuntimeException) error.getException();
                    }
                    return new Response.Builder<File>()
                            .withResponse(null)
                            .withHttpHeaders(error.getHeaders())
                            .withHttpStatus(error.getHttpStatus())
                            .build();
                })
                .setBearerToken(getAccessToken())
                .params("fields", "*")
                .build())
                .getResponse();
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

    public File downloadFile(final String fileId) {
        // need to fetch the file metadata first
        File file = get(fileId);
        if (file == null) {
            throw new IllegalArgumentException("Invalid file id");
        }
        // make a decision if the file is google type or other
        final String urlString = UriComponentsBuilder
                .fromHttpUrl(FILES_ENDPOINT)
                .path("/" + fileId)
                .queryParam("alt", "media")
                .build().toUriString();
        try {
            final URL url = new URL(urlString);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Bearer " + getAccessToken());
            InputStreamResource resource = new InputStreamResource(urlConnection.getInputStream());
            file.setFileContent(resource);
        } catch (IOException e) {
            throw new RuntimeException("Unable to get the file content for file id " + fileId);
        }
        return file;
    }

    private FileListResponse listFiles(SearchParams params) {
        if (StringUtils.isEmpty(params.getQuery())) {
            throw new IllegalArgumentException("Query must be specified");
        }
        Request.Builder<Void, FileListResponse> requestBuilder = new Request
                .Builder<Void, FileListResponse>(FILES_ENDPOINT, HttpMethod.GET, FileListResponse.class)
                .errorHandler(error -> { throw (RuntimeException) error.getException();})
                .setBearerToken(getAccessToken())
                .params("q", params.getQuery());

        if (StringUtils.isNotEmpty(params.getFieldsQuery())) {
            requestBuilder.params("fields", params.getFieldsQuery());
        }

        return restClient.execute(requestBuilder.build()).getResponse();
    }

    private String getAccessToken() {
        // TODO: Introduce a short-lived cache over here to minimize calls
        // use credentials to get an access token
        return getAccessToken(credentials.getProperty("google.clientId"),
                credentials.getProperty("google.clientSecret"),
                credentials.getProperty("google.refreshToken")).getAccessToken();
    }

    public Token getAccessToken(final String clientId, final String clientSecret, final String refreshToken) {
        final String refreshTokenEndpoint = GOOGLE_OAUTH2_ENDPOINT + "/token";
        final MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("refresh_token", refreshToken);
        formData.add("grant_type", "refresh_token");

        final Request<MultiValueMap<String, String>, Token> request = new Request
                .Builder<MultiValueMap<String, String>, Token>(refreshTokenEndpoint, HttpMethod.POST, Token.class)
                .body(formData)
                .errorHandler(error -> { throw (RuntimeException) error.getException();})
                .headers(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED.toString())
                .build();

        return restClient.execute(request).getResponse();
    }
}
