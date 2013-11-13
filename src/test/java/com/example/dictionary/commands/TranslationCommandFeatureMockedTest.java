package com.example.dictionary.commands;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.dictionary.CommandParameters;
import com.example.dictionary.TranslationProcess;
import com.example.dictionary.commands.TranslationCommand;
import com.example.dictionary.commands.TranslationCommandFeatureMockedTest.JavaConfiguration;
import com.example.dictionary.config.GenericTestConfiguration;
import com.example.dictionary.model.DictionaryWord;
import com.github.rmannibucau.featuredmock.http.FeaturedHttpServer;
import com.github.rmannibucau.featuredmock.http.FeaturedHttpServerBuilder;

@ContextConfiguration(classes = JavaConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TranslationCommandFeatureMockedTest {

	@Autowired
	BeanFactory factory;
	
	private static FeaturedHttpServer server; 
	
	@BeforeClass
	public static void before() {
		server = new FeaturedHttpServerBuilder().port(1234).build().start();
	}
	
	@AfterClass
	public static void after() {
		server.stop();
	}
	
	@Test
	public void bookTranslationTest() {
		TranslationProcess process = TranslationProcess.fromCommandParameters(new CommandParameters("search book"));
		TranslationCommand command = (TranslationCommand) factory.getBean(
				"translationCommand", process);
		process = command.execute();
		
		List<DictionaryWord> dictionaryWords = process.getWords();
		assertEquals(24, dictionaryWords.size());
		assertEquals("książka", dictionaryWords.get(1).getPolishWord());
	}
	
	
	@Configuration
	public static class JavaConfiguration extends GenericTestConfiguration {

		@Bean
		public PropertySourcesPlaceholderConfigurer serverConfiguration() {
			Properties props = new Properties();
			props.setProperty("urlStringTemplate", "http://localhost:1234/words/book.html");
			
			PropertySourcesPlaceholderConfigurer p = new PropertySourcesPlaceholderConfigurer();
			p.setProperties(props);
			return p;
		}
		
	}
}
