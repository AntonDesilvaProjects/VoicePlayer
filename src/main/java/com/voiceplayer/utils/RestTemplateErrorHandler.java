package com.voiceplayer.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.client.ClientHttpResponse;

import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

public class RestTemplateErrorHandler extends DefaultResponseErrorHandler {
    private static final Logger logger = LogManager.getLogger(RestTemplateErrorHandler.class);

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        // propagate the 4XX and 5XX errors to the caller
       return !response.getStatusCode().is5xxServerError() && !response.getStatusCode().is4xxClientError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        //logger.error("Some Error" + response.getBody());
        super.handleError(response);
    }
}
