package com.voiceplayer.common.restclient;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

public class Request<T> {

    private String url;
    private HttpMethod httpMethod;
    private HttpHeaders headers;
    private T body;
    private Class<T> clazz;
    private Response<T> errorHandler;

    private Request(Builder<T> builder) {
        this.url = builder.url;
        this.httpMethod = builder.httpMethod;
        this.headers = builder.headers;
        this.body = builder.body;
        this.clazz = builder.clazz;
        this.errorHandler = builder.errorHandler;
    }

    public static class Builder<T> {

        private String url;
        private HttpMethod httpMethod;
        private HttpHeaders headers;
        private T body;
        private Class<T> clazz;
        private Response<T> errorHandler;

        // only the URL and method is required
        Builder(String url, HttpMethod httpMethod) {
            this.url = url;
            this.httpMethod = httpMethod;
        }

        Builder(String url, HttpMethod httpMethod, HttpHeaders headers, T body, Class<T> clazz, Response<T> errorHandler) {
            this.url = url;
            this.httpMethod = httpMethod;
            this.headers = headers;
            this.body = body;
            this.clazz = clazz;
            this.errorHandler = errorHandler;
        }

        public Builder url(String url){
            this.url = url;
            return Builder.this;
        }

        public Builder httpMethod(HttpMethod httpMethod){
            this.httpMethod = httpMethod;
            return Builder.this;
        }

        public Builder headers(HttpHeaders headers){
            this.headers = headers;
            return Builder.this;
        }

        public Builder body(T body){
            this.body = body;
            return Builder.this;
        }

        public Builder clazz(Class<T> clazz){
            this.clazz = clazz;
            return Builder.this;
        }

        public Builder errorHandler(Response<T> errorHandler){
            this.errorHandler = errorHandler;
            return Builder.this;
        }

        public Request<T> build() {
            return new Request<T>(this);
        }
    }
}
