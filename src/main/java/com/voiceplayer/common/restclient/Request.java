package com.voiceplayer.common.restclient;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.util.function.Function;

public class Request<T, R> {
    private String url;
    private HttpMethod httpMethod;
    private HttpHeaders headers;
    private T body;
    private Class<R> responseType;
    private Function<Error, Response<R>> errorHandler;

    private Request(Builder<T, R> builder) {
        this.url = builder.url;
        this.httpMethod = builder.httpMethod;
        this.headers = builder.headers;
        this.body = builder.body;
        this.responseType = builder.responseType;
        this.errorHandler = builder.errorHandler;
    }

    public static class Builder<T, R> {

        private String url;
        private HttpMethod httpMethod;
        private HttpHeaders headers;
        private T body;
        private Class<R> responseType;
        private Function<Error, Response<R>> errorHandler;

        // only the URL and method is required
        public Builder(String url, HttpMethod httpMethod, Class<R> responseType) {
            this.url = url;
            this.httpMethod = httpMethod;
            this.responseType = responseType;
        }

        Builder(String url, HttpMethod httpMethod, HttpHeaders headers, T body, Class<R> clazz, Function<Error, Response<R>> errorHandler) {
            this.url = url;
            this.httpMethod = httpMethod;
            this.headers = headers;
            this.body = body;
            this.responseType = clazz;
            this.errorHandler = errorHandler;
        }

        public Builder<T, R> url(String url){
            this.url = url;
            return Builder.this;
        }

        public Builder<T, R> httpMethod(HttpMethod httpMethod){
            this.httpMethod = httpMethod;
            return Builder.this;
        }

        public Builder<T, R> headers(HttpHeaders headers){
            this.headers = headers;
            return Builder.this;
        }

        public Builder<T, R> body(T body){
            this.body = body;
            return Builder.this;
        }

        public Builder<T, R> clazz(Class<R> clazz){
            this.responseType = clazz;
            return Builder.this;
        }

        public Builder<T, R> errorHandler(Function<Error, Response<R>> errorHandler){
            this.errorHandler = errorHandler;
            return Builder.this;
        }

        public Request<T, R> build() {
            return new Request<>(this);
        }
    }

    public String getUrl() {
        return url;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public T getBody() {
        return body;
    }

    public Class<R> getResponseType() {
        return responseType;
    }

    public Function<Error, Response<R>> getErrorHandler() {
        return errorHandler;
    }
}
