package com.voiceplayer.common;

import com.voiceplayer.dao.AudioFileDao;
import com.voiceplayer.domain.AudioFile;
import com.voiceplayer.domain.AudioFileResponse;
import com.voiceplayer.domain.AudioFileSearchParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *  DAO implementation responsible for interfacing with Google Drive to access users audio
 *  files
 * */
@Repository
public class GoogleDriveService {

    private final RestTemplate restTemplate;
    private final String GOOGLE_DRIVE_V3_ENDPOINT;

    public GoogleDriveService(RestTemplate restTemplate, @Value("${google.drive.endpoint}") String googleDriveAPIEndpoint) {
        this.restTemplate = restTemplate;
        this.GOOGLE_DRIVE_V3_ENDPOINT = googleDriveAPIEndpoint;
    }

    public AudioFile get(String id) {
        return null;
    }

    public AudioFileResponse search(AudioFileSearchParams params) {
        // build the search request
        final HttpHeaders headers = buildHeaders("Authorization", "Bearer ");
        final String apiEndpoint = GOOGLE_DRIVE_V3_ENDPOINT + "/files";

        final String url = UriComponentsBuilder
                .fromHttpUrl(apiEndpoint)
                .queryParam("q", params.getQuery())
                .build()
                .toUriString();

        ResponseEntity<AudioFileResponse> response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), AudioFileResponse.class);

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
}
