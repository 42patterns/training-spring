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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.sql.DataSource;

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
    @EnableTransactionManagement
	@EnableAspectJAutoProxy
	public static class AppConfiguration {
	
		@Bean(name="validator")
		public LocalValidatorFactoryBean validator() {
			return new LocalValidatorFactoryBean();
		}
		
		@Bean
		public DataSource dataSource() {
			DriverManagerDataSource ds = new DriverManagerDataSource();
			ds.setDriverClassName(com.mysql.jdbc.Driver.class.getName());
			ds.setUrl("jdbc:mysql://localhost:3306/translations?useUnicode=true&characterEncoding=utf-8");
			ds.setUsername("root");
			ds.setPassword("root");
			return ds;
		}

        @Bean
        public DataSourceTransactionManager transactionManager(DataSource ds) {
            return new DataSourceTransactionManager(ds);
        }
		
		@Bean
		public PropertySourcesPlaceholderConfigurer properties() {
			return new PropertySourcesPlaceholderConfigurer();
		}
	
	}

}
