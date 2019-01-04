package br.com.edson.microservices.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableEurekaClient
@EnableJpaRepositories(basePackages = "br.com.edson.microservices.cloud.repository")
@EntityScan(basePackages = {"br.com.edson.microservices.cloud.entity"} )
@SpringBootApplication
public class DbClienteServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbClienteServerApplication.class, args);
	}

}

