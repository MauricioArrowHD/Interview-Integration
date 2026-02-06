package com.arrowhead.integration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        // In a real scenario, this might point to a gateway or specific services.
        // For this self-contained test, it points to localhost where Mock Controllers
        // run.
        return builder
                .baseUrl("http://localhost:8080")
                .build();
    }
}
