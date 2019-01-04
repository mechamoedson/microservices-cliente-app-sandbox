package br.com.edson.microservices.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableCircuitBreaker
@EnableEurekaClient
@SpringBootApplication
public class ClienteClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClienteClientApplication.class, args);
	}

}