package com.voiceplayer.common.restclient;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

public class Error {
   private HttpStatus httpStatus;
   private HttpHeaders headers;
   private String response;
   private Exception exception;

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Error setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public Error setHeaders(HttpHeaders headers) {
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
