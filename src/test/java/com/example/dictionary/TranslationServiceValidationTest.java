package com.example.dictionary;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.example.AppJavaConfig;

@ContextConfiguration(classes = {TranslationService.class, LocalValidatorFactoryBean.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class TranslationServiceValidationTest {

	@Autowired
	TranslationService service;
	
	@Test
	public void noAttributesCommand() {
		CommandParameters params = new CommandParameters("search");
		
		Set<ConstraintViolation<CommandParameters>> errors = service.validate(params);
		assertThat(errors.isEmpty(), is(false));
		assertThat(errors.size(), is(1));
		
		ConstraintViolation<CommandParameters> violation = errors.iterator().next();
		assertThat(violation.getMessageTemplate(), is("{javax.validation.constraints.Size.message}"));
	}
	
	@Test
	public void validCommand() {
		CommandParameters params = new CommandParameters("search book");
		
		Set<ConstraintViolation<CommandParameters>> errors = service.validate(params);
		assertThat(errors.isEmpty(), is(true));
	}
	
}
