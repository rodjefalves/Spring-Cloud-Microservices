package br.com.conductor.crudservice;

import br.com.conductor.crudservice.config.ApplicationProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableConfigurationProperties(ApplicationProperty.class)
public class CrudServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudServiceApplication.class, args);
	}

}
