package com.voiceplayer.common.restclient;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

/**
 *  Wrapper around Spring's RestTemplate to simplify making API calls and
 *  handling errors
 * */
public class RestTemplateClient implements RestClient {
    final RestTemplate restTemplate;

    public RestTemplateClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public <T, R> Response<R> execute(Request<T, R> request) {
        HttpEntity<T> entity = new HttpEntity<>(request.getBody(), request.getHttpHeaders());
        ResponseEntity<R> responseEntity;
        Response<R> response = null;
        try {
            responseEntity = restTemplate.exchange(request.getUrl(),
                    request.getHttpMethod(), entity, request.getResponseType());
            // build response from the responseEntity
            response = new Response.Builder<R>()
                    .withHttpStatus(responseEntity.getStatusCode())
                    .withHttpHeaders(responseEntity.getHeaders())
                    .withResponse(responseEntity.getBody())
                    .build();
        } catch (RestClientResponseException exception) {
            response = Optional.ofNullable(request.getErrorHandler())
                    .map(handler -> handler.apply(errorFrom(exception)))
                    .orElseThrow(() -> exception);
        }
        return response;
    }

    private Error errorFrom(RestClientResponseException exception) {
        return new Error()
                .setException(exception)
                .setHttpStatus(HttpStatus.resolve(exception.getRawStatusCode()))
                .setHeaders(exception.getResponseHeaders())
                .setResponse(exception.getResponseBodyAsString());
    }
}
