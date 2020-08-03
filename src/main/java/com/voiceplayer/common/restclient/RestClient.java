package com.voiceplayer.common.restclient;

public interface RestClient {
    <T> Response<T> execute(Request<T> request);
}
