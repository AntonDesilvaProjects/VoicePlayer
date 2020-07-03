package com.voiceplayer.common.googledrive.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Token {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("expires_in")
    private int expiresIn;
    @JsonProperty("scope")
    private String scope;
    @JsonProperty("token_type")
    private String tokenType;

    public String getAccessToken() {
        return accessToken;
    }

    public Token setAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public Token setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    public String getScope() {
        return scope;
    }

    public Token setScope(String scope) {
        this.scope = scope;
        return this;
    }

    public String getTokenType() {
        return tokenType;
    }

    public Token setTokenType(String tokenType) {
        this.tokenType = tokenType;
        return this;
    }
}
