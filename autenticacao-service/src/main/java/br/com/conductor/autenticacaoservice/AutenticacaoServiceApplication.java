package br.com.conductor.autenticacaoservice;

import br.com.conductor.autenticacaoservice.config.ApplicationProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties(ApplicationProperty.class)
public class AutenticacaoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutenticacaoServiceApplication.class, args);
	}

}
