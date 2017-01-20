package com.example;

import com.example.dictionary.Controller;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppJavaConfig {

	public static void main(String... args) {

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.getEnvironment().setActiveProfiles("jpa");
        ctx.register(AppConfiguration.class);
        ctx.refresh();

        Controller c = ctx.getBean(Controller.class);
		c.run();

		ctx.close();
	}

}
