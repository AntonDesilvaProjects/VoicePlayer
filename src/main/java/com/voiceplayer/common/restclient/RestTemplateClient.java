package com.voiceplayer.common.restclient;

/**
 *  Simple wrapper around Spring's RestTemplate to simplify making API calls and
 *  handling errors
 * */
public class RestTemplateClient implements RestClient {
    @Override
    public <T> Response<T> execute(Request<T> request) {
        return null;
    }
}
