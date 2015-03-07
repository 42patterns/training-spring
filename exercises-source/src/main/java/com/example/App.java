package com.example;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.example.dictionary.Controller;

public class App {

	public static void main(String... args) {

		AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("META-INF/spring/app-context.xml");
		Controller c = ctx.getBean(Controller.class);
		c.run();
		
		ctx.close();
	}
	
}
