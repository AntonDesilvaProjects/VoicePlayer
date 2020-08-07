package com.voiceplayer.common.restclient;

public interface RestClient {
    <T, R> Response<R> execute(Request<T, R> request);
}
