package com.voiceplayer.common.restclient;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class Request<T, R> {
    private String url;
    private HttpMethod httpMethod;
    private Multimap<String, String> httpHeaders;
    private T body;
    private Class<R> responseType;
    private Function<Error, Response<R>> errorHandler;

    private Request(Builder<T, R> builder) {
        this.url = builder.url;
        this.httpMethod = builder.httpMethod;
        this.httpHeaders = builder.httpHeaders;
        this.body = builder.body;
        this.responseType = builder.responseType;
        this.errorHandler = builder.errorHandler;
    }

    public static class Builder<T, R> {

        private String url;
        private HttpMethod httpMethod;
        private Multimap<String, String> httpHeaders = ArrayListMultimap.create();
        private T body;
        private Class<R> responseType;
        private Function<Error, Response<R>> errorHandler;
        private Map<String, String> paramsMap = new HashMap<>();


        // only the URL and method is required
        public Builder(String url, HttpMethod httpMethod, Class<R> responseType) {
            this.url = url;
            this.httpMethod = httpMethod;
            this.responseType = responseType;
        }

        public Builder<T, R> url(String url){
            this.url = url;
            return Builder.this;
        }

        public Builder<T, R> httpMethod(HttpMethod httpMethod){
            this.httpMethod = httpMethod;
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

        public Builder<T, R> params(String ...params) {
            if (params.length == 0 || params.length % 2 != 0) {
                throw new IllegalArgumentException("Invalid number of param name-value pairs!");
            }
            for (int i = 0; i < params.length; i = i + 2) {
                paramsMap.put(params[i], params[i+1]);
            }
            return Builder.this;
        }

        public Builder<T, R> headers(String ...headers) {
            if (headers.length == 0 || headers.length % 2 != 0) {
                throw new IllegalArgumentException("Invalid number of param name-value pairs!");
            }
            for (int i = 0; i < headers.length; i = i + 2) {
                httpHeaders.put(headers[i], headers[i+1]);
            }
            return Builder.this;
        }

        public Builder<T, R> setBearerToken(String token) {
            httpHeaders.put("Authorization", "Bearer " + token);
            return Builder.this;
        }

        public Request<T, R> build() {
            // do validations
            Assert.isTrue(StringUtils.isNotEmpty(url), "URL must be specified");
            Assert.notNull(responseType, "Response class type must be specified. Specify Void.class if no response type");
            Assert.notNull(httpMethod, "HTTP method must be specified");

            // build the url
            if (MapUtils.isNotEmpty(paramsMap)) {
                final UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url);
                for (String paramName: paramsMap.keySet()) {
                    uriBuilder.queryParam(paramName, paramsMap.get(paramName));
                }
                this.url = uriBuilder.build().toUriString();
            }
            return new Request<>(this);
        }
    }

    public String getUrl() {
        return url;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public Multimap<String, String> getHttpHeaders() {
        return httpHeaders;
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
