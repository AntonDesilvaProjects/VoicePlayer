package com.voiceplayer.common.restclient;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

public class Response<T> {
    private T response;
    private HttpHeaders httpHeaders;
    private HttpStatus httpStatus;

    private Response(Builder<T> builder) {
        response = builder.response;
        httpHeaders = builder.httpHeaders;
        httpStatus = builder.httpStatus;
    }

    public static final class Builder<T> {
        private T response;
        private HttpHeaders httpHeaders;
        private HttpStatus httpStatus;

        public Builder() {
        }

        public Builder<T> withResponse(T val) {
            response = val;
            return this;
        }

        public Builder<T> withHttpHeaders(HttpHeaders val) {
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

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
