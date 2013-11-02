package com.example;

import com.example.dictionary.Controller;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;


public class AppJavaConfig {

	public static void main(String... args) {

		AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(
				AppConfiguration.class);
		Controller c = ctx.getBean(Controller.class);
		c.run();

		ctx.close();
	}
	
	@Configuration
	@ComponentScan(value = { "com.example.dictionary", "com.example.helloworld" }, 
		excludeFilters = @ComponentScan.Filter(
				value = Configuration.class, 
				type = FilterType.ANNOTATION)
	)
    @PropertySource("META-INF/spring/dict.properties")
	@EnableAspectJAutoProxy
	public static class AppConfiguration {
	
		@Bean(name="validator")
		public LocalValidatorFactoryBean validator() {
			return new LocalValidatorFactoryBean();
		}
		
		@Bean
		public PropertySourcesPlaceholderConfigurer properties() {
			return new PropertySourcesPlaceholderConfigurer();
		}
	
	}

}
