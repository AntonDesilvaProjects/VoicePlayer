package com.voiceplayer.config;

import com.voiceplayer.utils.ApplicationUtils;
import com.voiceplayer.utils.RestTemplateErrorHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Properties;

@Configuration
public class ApplicationConfigs {
    private final String CREDENTIALS_PATH;

    public ApplicationConfigs(@Value("${google.credential-config-path}") String googleCredentialConfigPath) {
        this.CREDENTIALS_PATH = googleCredentialConfigPath;
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplateBuilder()
                //.errorHandler(new RestTemplateErrorHandler())
                .setConnectTimeout(Duration.ofMinutes(1))
                .setReadTimeout(Duration.ofMinutes(1))
                .build();
    }

    @Bean("credentials")
    public Properties getCredentials() {
        return ApplicationUtils.loadPropertiesFile(CREDENTIALS_PATH);
    }
}
