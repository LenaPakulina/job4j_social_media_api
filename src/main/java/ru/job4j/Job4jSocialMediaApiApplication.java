package ru.job4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import ru.job4j.repository.UserRepository;

@SpringBootApplication
public class Job4jSocialMediaApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(Job4jSocialMediaApiApplication.class, args);
	}
}
