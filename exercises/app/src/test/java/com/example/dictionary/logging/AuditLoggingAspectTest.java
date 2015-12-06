package com.example.dictionary.logging;

import com.example.AppJavaConfig;
import com.example.dictionary.CommandParameters;
import com.example.dictionary.TranslationProcess;
import com.example.dictionary.commands.TranslationCommand;
import com.example.dictionary.commands.TranslationCommandLocalFileTest;
import com.example.dictionary.config.GenericTestConfiguration;
import com.example.dictionary.translation.TranslationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Properties;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = AuditLoggingAspectTest.AspectConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class AuditLoggingAspectTest {

	@Autowired
	BeanFactory factory;
	
	@Autowired
	AuditLoggingAspect aspect;
	
	@Test
	public void aspectIsInvoked() {
		TranslationProcess process = TranslationProcess.fromCommandParameters(new CommandParameters("search book"));
		TranslationCommand command = factory.getBean(TranslationCommand.class, process);

		command.execute();
		
		verify(aspect.log).info(isA(Class.class), anyString());
	}

	@Configuration
	@ComponentScan(value = "com.example.dictionary", excludeFilters = @ComponentScan.Filter(value = LoggerWrapper.class, type = FilterType.ASSIGNABLE_TYPE))
	@EnableAspectJAutoProxy
	public static class AspectConfiguration extends GenericTestConfiguration {

		@Bean
		public LocalValidatorFactoryBean validator() {
			return new LocalValidatorFactoryBean();
		}

		@Bean
		public LoggerWrapper logger() {
			return spy(new LoggerWrapper());
		}

		@Bean
		public PropertySourcesPlaceholderConfigurer serverConfiguration() {
			Properties props = new Properties();
			props.setProperty("urlStringTemplate", AspectConfiguration.class.getResource("/words/book.html").toExternalForm());
			
			PropertySourcesPlaceholderConfigurer p = new PropertySourcesPlaceholderConfigurer();
			p.setProperties(props);
			return p;
		}

		
	}
	
}

