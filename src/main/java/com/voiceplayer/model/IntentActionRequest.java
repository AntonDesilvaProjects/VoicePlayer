package com.voiceplayer.model;

import com.voiceplayer.common.witai.model.IntentResolutionResponse;

public class IntentActionRequest {
    private IntentResolutionResponse intentResolutionResponse;
    private IntentEntityMapping intentEntityMapping;

    public IntentResolutionResponse getIntentResolutionResponse() {
        return intentResolutionResponse;
    }

    public void setIntentResolutionResponse(IntentResolutionResponse intentResolutionResponse) {
        this.intentResolutionResponse = intentResolutionResponse;
    }

    public IntentEntityMapping getIntentEntityMapping() {
        return intentEntityMapping;
    }

    public void setIntentEntityMapping(IntentEntityMapping intentEntityMapping) {
        this.intentEntityMapping = intentEntityMapping;
    }
}
