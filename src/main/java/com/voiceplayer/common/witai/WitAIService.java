package com.voiceplayer.common.witai;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class WitAIService {
    private final Properties credentials;

    public WitAIService(@Qualifier("credentials") Properties credentials) {
        this.credentials = credentials;
    }
}
