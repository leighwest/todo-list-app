package com.west.todoAPI;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title="Todo API", description="An API to create and manage todo lists"))
@EnableDiscoveryClient
public class TodoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoApiApplication.class, args);
	}

}
