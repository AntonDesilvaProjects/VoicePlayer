package com.voiceplayer.exception;

public class UnexpectedIntentException extends IntentException {
    public UnexpectedIntentException() {
        super(null);
    }
    public UnexpectedIntentException(String message) {
        super(message);
    }
}
