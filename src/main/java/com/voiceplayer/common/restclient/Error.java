package com.voiceplayer.common.restclient;

import com.google.common.collect.Multimap;
import org.springframework.http.HttpHeaders;

public class Error {
   private HttpStatus httpStatus;
   private Multimap<String, String> headers;
   private String response;
   private Exception exception;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Error setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

    public Multimap<String, String> getHeaders() {
        return headers;
    }

    public Error setHeaders(Multimap<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public String getResponse() {
        return response;
    }

    public Error setResponse(String response) {
        this.response = response;
        return this;
    }

    public Exception getException() {
        return exception;
    }

    public Error setException(Exception exception) {
        this.exception = exception;
        return this;
    }
}
