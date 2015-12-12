package com.example.dictionary.validation;

import com.example.dictionary.CommandParameters;
import com.example.dictionary.TranslationProcess;
import com.example.dictionary.commands.Command;
import com.example.dictionary.commands.TranslationCommand;
import com.example.dictionary.translation.TranslationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@ContextConfiguration(classes = {TranslationCommand.class, TranslationService.class, LocalValidatorFactoryBean.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class TranslationCommandValidationTest {

	@Autowired
	BeanFactory factory;
	
	@Test
	public void noAttributesCommand() {
		TranslationProcess process = TranslationProcess.fromCommandParameters(new CommandParameters("search"));
		TranslationCommand service = factory.getBean(TranslationCommand.class, process);
		
		Set<ConstraintViolation<? extends Command>> errors = service.getErrors();
		assertThat(errors.isEmpty(), is(false));
		assertThat(errors.size(), is(1));
		
		ConstraintViolation<?> violation = errors.iterator().next();
		assertThat(violation.getMessageTemplate(), is("{javax.validation.constraints.Size.message}"));
	}
	
	@Test
	public void validCommand() {
		TranslationProcess process = TranslationProcess.fromCommandParameters(new CommandParameters("search book"));
		TranslationCommand service = factory.getBean(TranslationCommand.class, process);

		
		Set<ConstraintViolation<? extends Command>> errors = service.getErrors();
		assertThat(errors.isEmpty(), is(true));
	}
	
}
