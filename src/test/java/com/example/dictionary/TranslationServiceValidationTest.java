package com.example.dictionary;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.example.dictionary.commands.TranslationCommand;

@ContextConfiguration(classes = {TranslationCommand.class, LocalValidatorFactoryBean.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class TranslationServiceValidationTest {

	@Autowired
	BeanFactory factory;
	
	@Test
	public void noAttributesCommand() {
		TranslationCommand service = (TranslationCommand) factory.getBean(
				"translationCommand", new CommandParameters("search"));
		
		Set<ConstraintViolation<CommandParameters>> errors = service.getErrors();
		assertThat(errors.isEmpty(), is(false));
		assertThat(errors.size(), is(1));
		
		ConstraintViolation<CommandParameters> violation = errors.iterator().next();
		assertThat(violation.getMessageTemplate(), is("{javax.validation.constraints.Size.message}"));
	}
	
	@Test
	public void validCommand() {
		TranslationCommand service = (TranslationCommand) factory.getBean(
				"translationCommand", new CommandParameters("search book"));

		
		Set<ConstraintViolation<CommandParameters>> errors = service.getErrors();
		assertThat(errors.isEmpty(), is(true));
	}
	
}
