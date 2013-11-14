package com.example.dictionary.logging;

import static org.mockito.Mockito.*;

import java.util.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.dictionary.CommandParameters;
import com.example.dictionary.TranslationProcess;
import com.example.dictionary.commands.TranslationCommand;
import com.example.dictionary.commands.TranslationCommandLocalFileTest.JavaConfiguration;
import com.example.dictionary.config.GenericTestConfiguration;

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
		TranslationCommand command = (TranslationCommand) factory.getBean(
				"translationCommand", process);

		command.execute();
		
		verify(aspect.log).info(isA(Class.class), anyString());
	}

	@Configuration
	@EnableAspectJAutoProxy
	public static class AspectConfiguration extends GenericTestConfiguration {
		
		@Bean
		public LoggerWrapper logger() {
			return spy(new LoggerWrapper());
		}
		
		@Bean
		public AuditLoggingAspect aspect() {
			return new AuditLoggingAspect();
		}
		
		@Bean
		public PropertySourcesPlaceholderConfigurer serverConfiguration() {
			Properties props = new Properties();
			props.setProperty("urlStringTemplate", JavaConfiguration.class.getResource("/words/book.html").toExternalForm());
			
			PropertySourcesPlaceholderConfigurer p = new PropertySourcesPlaceholderConfigurer();
			p.setProperties(props);
			return p;
		}

		
	}
	
}

