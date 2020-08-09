package com.voiceplayer.common.restclient;

import com.google.common.collect.Multimap;
import org.springframework.http.HttpHeaders;

public class Response<T> {
    private T response;
    private Multimap<String, String> httpHeaders;
    private HttpStatus httpStatus;

    private Response(Builder<T> builder) {
        response = builder.response;
        httpHeaders = builder.httpHeaders;
        httpStatus = builder.httpStatus;
    }

    public static final class Builder<T> {
        private T response;
        private Multimap<String, String> httpHeaders;
        private HttpStatus httpStatus;

        public Builder() {
        }

        public Builder<T> withResponse(T val) {
            response = val;
            return this;
        }

        public Builder<T> withHttpHeaders(Multimap<String, String> val) {
            httpHeaders = val;
            return this;
        }

        public Builder<T> withHttpStatus(HttpStatus val) {
            httpStatus = val;
            return this;
        }

        public Response<T> build() {
            return new Response<T>(this);
        }
    }

    public T getResponse() {
        return response;
    }

    public Multimap<String, String> getHttpHeaders() {
        return httpHeaders;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
