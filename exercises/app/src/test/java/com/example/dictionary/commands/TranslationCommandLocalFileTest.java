package com.example.dictionary.commands;

import com.example.AppConfiguration;
import com.example.dictionary.CommandParameters;
import com.example.dictionary.TranslationProcess;
import com.example.dictionary.model.DictionaryWord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.mock.env.MockPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(classes = AppConfiguration.class,
        initializers = TranslationCommandLocalFileTest.LocalFilePropertyApplicationContextInitializer.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TranslationCommandLocalFileTest {

	@Autowired
	BeanFactory factory;
	
	@Test
	public void bookTranslationTest() {
		TranslationProcess process = TranslationProcess.fromCommandParameters(CommandParameters.from("search book"));
		TranslationCommand command = factory.getBean(TranslationCommand.class, process);
		process = command.execute();

		List<DictionaryWord> dictionaryWords = process.getWords();
		
		assertEquals(24, dictionaryWords.size());
		assertEquals("książka", dictionaryWords.get(1).getPolishWord());
	}


    public static class LocalFilePropertyApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            PropertiesPropertySource properties = new MockPropertySource().withProperty("urlStringTemplate",
                    LocalFilePropertyApplicationContextInitializer.class.getResource("/words/book.html").toExternalForm());
            applicationContext.getEnvironment().getPropertySources().replace(StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME, properties);

        }
    }

}
