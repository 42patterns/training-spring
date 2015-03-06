package com.example;

import com.example.dictionary.Controller;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

	public static void main(String... args) {

        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext();
        ctx.getEnvironment().setActiveProfiles("jpa");
        ctx.setConfigLocation("META-INF/spring/app-context.xml");
        ctx.refresh();

        Controller c = ctx.getBean(Controller.class);
		c.run();
		
		ctx.close();
	}
	
}
