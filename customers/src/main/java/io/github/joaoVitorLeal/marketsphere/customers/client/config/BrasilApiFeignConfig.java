package io.github.joaoVitorLeal.marketsphere.customers.client.config;

import io.github.joaoVitorLeal.marketsphere.customers.client.error.BrasilApiErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BrasilApiFeignConfig {

    @Bean
    public BrasilApiErrorDecoder brasilApiErrorDecoder() {
        return new BrasilApiErrorDecoder();
    }
}
