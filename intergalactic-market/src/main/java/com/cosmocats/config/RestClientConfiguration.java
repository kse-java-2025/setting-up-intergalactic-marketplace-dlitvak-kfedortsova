package com.cosmocats.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Slf4j
@Configuration
public class RestClientConfiguration {

    private final int responseTimeout;

    public RestClientConfiguration(
            @Value("${application.restclient.response-timeout:1000}") int responseTimeout
    ) {
        this.responseTimeout = responseTimeout;
    }

    @Bean("productRestClient")
    public RestClient productRestClient() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(responseTimeout);
        factory.setConnectTimeout(responseTimeout);

        return RestClient.builder()
                .requestFactory(factory)
                .build();
    }
}
