package br.com.conductor.estoque;

import br.com.conductor.estoque.config.ApplicationProperty;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableRabbit
@EnableConfigurationProperties(ApplicationProperty.class)
public class EstoqueApplication {

	//@Value("${queue.order.name}")
	//private String orderQueue;

	public static void main(String[] args) {
		SpringApplication.run(EstoqueApplication.class, args);
	}

	//@Bean
	//public Queue queue() {
	//	return new Queue(orderQueue, true);
	//}
}
