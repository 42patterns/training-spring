package com.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.example.dictionary.Service;

public class App {

	public static void main(String... args) {

		ApplicationContext ctx = new ClassPathXmlApplicationContext("META-INF/spring/app-context.xml");
		Service service = ctx.getBean(Service.class);
		
		System.out.println(service.getMessage());
	}
	
}
