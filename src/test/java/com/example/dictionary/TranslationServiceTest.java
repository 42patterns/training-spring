package com.example.dictionary;

import com.example.dictionary.model.DictionaryWord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@ContextConfiguration(classes = TranslationServiceTest.JavaConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TranslationServiceTest {

	@Autowired
	TranslationService service;
	
	@Test
	public void bookTranslationTest() {
		List<DictionaryWord> dictionaryWords = service.getDictionaryWords("search book");
		
		assertEquals(24, dictionaryWords.size());
		assertEquals("książka", dictionaryWords.get(1).getPolishWord());
	}
	
	
	@Configuration
    @PropertySource("META-INF/spring/dict.properties")
    public static class JavaConfiguration {

        @Bean
        public PropertySourcesPlaceholderConfigurer properties() {
            return new PropertySourcesPlaceholderConfigurer();
        }

        @Bean
        public TranslationService service() {
            return new TranslationService();
        }
		
	}
}
