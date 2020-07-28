package com.voiceplayer.model;

public class IntentActionResponse<T> {
    private boolean isSuccessful = true;
    private String responseText;
    private T response;

    public String getResponseText() {
        return responseText;
    }

    public IntentActionResponse setResponseText(String responseText) {
        this.responseText = responseText;
        return this;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
