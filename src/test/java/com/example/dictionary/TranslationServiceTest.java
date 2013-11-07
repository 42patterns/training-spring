package com.example.dictionary;

import com.example.dictionary.commands.TranslationCommand;
import com.example.dictionary.config.GenericTestConfiguration;
import com.example.dictionary.model.DictionaryWord;
import com.example.dictionary.model.TranslationProcess;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
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
	BeanFactory factory;

	@Test
	public void bookTranslationTest() {
		TranslationProcess process = new TranslationProcess();
		TranslationCommand command = (TranslationCommand) factory.getBean(
				"translationCommand", new CommandParameters("search book"));
		process = command.execute(process);

		List<DictionaryWord> dictionaryWords = process.getWords();
		assertEquals(24, dictionaryWords.size());
		assertEquals("książka", dictionaryWords.get(1).getPolishWord());
	}
	
	
	@Configuration
    @PropertySource("META-INF/spring/dict.properties")
    public static class JavaConfiguration extends GenericTestConfiguration {

        @Bean
        public PropertySourcesPlaceholderConfigurer properties() {
            return new PropertySourcesPlaceholderConfigurer();
        }

	}
}
