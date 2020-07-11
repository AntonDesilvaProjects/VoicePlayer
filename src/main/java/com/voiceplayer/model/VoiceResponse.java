package com.voiceplayer.model;

public class VoiceResponse<T> {
    private T result;

    public T getResult() {
        return result;
    }

    public VoiceResponse<T> setResult(T result) {
        this.result = result;
        return this;
    }
}
