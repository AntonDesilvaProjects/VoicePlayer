package com.voiceplayer.model;

import com.voiceplayer.common.witai.model.IntentResolutionResponse;

public class IntentActionRequest {
    private IntentResolutionResponse intentResolutionResponse;
    private IntentEntityMapping intentEntityMapping;

    public static class builder {
        private IntentResolutionResponse intentResponse;
        private IntentEntityMapping entityMapping;

        public builder withIntentResolutionResponse(IntentResolutionResponse intentResolutionResponse) {
            this.intentResponse = intentResolutionResponse;
            return this;
        }

        public builder withIntentEntityMapping(IntentEntityMapping intentEntityMapping) {
            this.entityMapping = intentEntityMapping;
            return this;
        }

        public IntentActionRequest build() {
            if (intentResponse == null || entityMapping == null) {
                throw new IllegalArgumentException("");
            }
            IntentActionRequest request = new IntentActionRequest();
            request.intentEntityMapping = entityMapping;
            request.intentResolutionResponse = intentResponse;
            return request;
        }
    }

    public IntentResolutionResponse getIntentResolutionResponse() {
        return intentResolutionResponse;
    }
    public IntentEntityMapping getIntentEntityMapping() {
        return intentEntityMapping;
    }
}
