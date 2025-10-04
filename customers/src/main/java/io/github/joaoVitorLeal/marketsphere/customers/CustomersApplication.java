package io.github.joaoVitorLeal.marketsphere.customers;

import io.github.joaoVitorLeal.marketsphere.customers.client.brasilapi.BrasilApiClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(
        basePackages = "io.github.joaoVitorLeal.marketsphere.customers.client",
        clients = BrasilApiClient.class
)
public class CustomersApplication {

	public static void main(String[] args) {
		SpringApplication.run(CustomersApplication.class, args);
	}

}
