package com.simplesystem.todoitems;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "SimpleSystem Todo API",
				description = "This service contains API(s) to manage todo items",
				contact = @Contact(
						name = "SimpleSystem",
						email = "sarathnambradath@gmail.com"
				)),
		servers = @Server(url = "http://localhost:8080"))
public class TodoItemsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoItemsApplication.class, args);
	}

}
