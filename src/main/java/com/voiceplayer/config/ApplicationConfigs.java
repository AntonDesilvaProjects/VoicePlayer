package com.voiceplayer.config;

import com.voiceplayer.utils.ApplicationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Properties;

@Configuration
public class ApplicationConfigs {
    private final String CREDENTIALS_PATH;

    public ApplicationConfigs(@Value("${google.credential-config-path}") String googleCredentialConfigPath) {
        this.CREDENTIALS_PATH = googleCredentialConfigPath;
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean("credentials")
    public Properties getCredentials() {
        return ApplicationUtils.loadPropertiesFile(CREDENTIALS_PATH);
    }
}
