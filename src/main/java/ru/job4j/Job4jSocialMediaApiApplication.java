package ru.job4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import ru.job4j.repository.UserRepository;

@SpringBootApplication
@ComponentScan(basePackages = {"ru.job4j.repository"})
public class Job4jSocialMediaApiApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Job4jSocialMediaApiApplication.class);
	}

	public static void main(String[] args) {
		var context = SpringApplication.run(Job4jSocialMediaApiApplication.class, args);
		System.out.println(context.getBean(UserRepository.class));
	}

}
