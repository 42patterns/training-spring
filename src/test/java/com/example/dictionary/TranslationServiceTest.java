package com.example.dictionary;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.dictionary.model.DictionaryWord;

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
	@ComponentScan("com.example.dictionary")
	public static class JavaConfiguration {
		
	}
}
