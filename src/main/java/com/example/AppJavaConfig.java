package com.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.example.helloworld.Service;

public class AppJavaConfig {

	public static void main(String... args) {

		ApplicationContext ctx = new AnnotationConfigApplicationContext(
				AppConfiguration.class);
		Service service = ctx.getBean(Service.class);

		System.out.println(service.getMessage());
	}

	@Configuration
	@ComponentScan("com.example")
	public static class AppConfiguration {

	}

}
