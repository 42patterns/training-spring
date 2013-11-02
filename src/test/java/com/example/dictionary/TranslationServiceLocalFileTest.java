package com.example.dictionary;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.dictionary.TranslationServiceLocalFileTest.JavaConfiguration;
import com.example.dictionary.config.GenericTestConfiguration;
import com.example.dictionary.model.DictionaryWord;

@ContextConfiguration(classes = JavaConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TranslationServiceLocalFileTest {

	@Autowired
	TranslationService service;
	
	@Test
	public void bookTranslationTest() {
		List<DictionaryWord> dictionaryWords = service.getDictionaryWords(new CommandParameters("search book"));
		
		assertEquals(24, dictionaryWords.size());
		assertEquals("książka", dictionaryWords.get(1).getPolishWord());
	}
	
	
	@Configuration
	public static class JavaConfiguration extends GenericTestConfiguration {

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
