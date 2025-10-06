package io.github.joaoVitorLeal.marketsphere.orders.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "io/github/joaoVitorLeal/marketsphere/orders/client")
public class ClientsConfig { }
