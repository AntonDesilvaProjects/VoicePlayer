package com.voiceplayer.common.restclient;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
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
        HttpEntity<T> entity = new HttpEntity<>(request.getBody(), toSpringHeaders(request.getHttpHeaders()));
        ResponseEntity<R> responseEntity;
        Response<R> response = null;
        try {
            responseEntity = restTemplate.exchange(request.getUrl(), toSpringHttpMethod(request.getHttpMethod()), entity, request.getResponseType());
            // build response from the responseEntity
            response = new Response.Builder<R>()
                    .withHttpStatus(toHttpStatus(responseEntity.getStatusCode()))
                    .withHttpHeaders(toHeaders(responseEntity.getHeaders()))
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
                .setHttpStatus(com.voiceplayer.common.restclient.HttpStatus.resolve(exception.getRawStatusCode()))
                .setHeaders(toHeaders(exception.getResponseHeaders()))
                .setResponse(exception.getResponseBodyAsString());
    }

    /*
    * Converter methods to interchange between the 'generic' auxiliary classes from the RestClient's Request/Response
    * objects and the Spring's implementation specific versions
    * */
    private HttpHeaders toSpringHeaders(Multimap<String, String> httpHeaders) {
        if (httpHeaders == null) {
            return null;
        }
        final HttpHeaders springHeadersObj = new HttpHeaders();
        for (String key: httpHeaders.keySet()) {
            springHeadersObj.addAll(key, new ArrayList<>(httpHeaders.get(key)));
        }
        return springHeadersObj;
    }

    private HttpMethod toSpringHttpMethod(com.voiceplayer.common.restclient.HttpMethod httpMethod) {
        return HttpMethod.resolve(httpMethod.name());
    }

    private Multimap<String, String> toHeaders(HttpHeaders springHeaders) {
        if (springHeaders == null) {
            return null;
        }
        Multimap<String, String> headers = ArrayListMultimap.create();
        for (String headerName : springHeaders.keySet()) {
            headers.putAll(headerName, springHeaders.getValuesAsList(headerName));
        }
        return headers;
    }

    private com.voiceplayer.common.restclient.HttpStatus toHttpStatus(HttpStatus httpStatus) {
        return com.voiceplayer.common.restclient.HttpStatus.valueOf(httpStatus.name());
    }
}
