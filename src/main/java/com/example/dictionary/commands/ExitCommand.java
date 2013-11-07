package com.example.dictionary.commands;

import java.util.Collections;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.dictionary.CommandParameters;
import com.example.dictionary.model.TranslationProcess;

@Component
@Scope(value=BeanDefinition.SCOPE_PROTOTYPE)
public class ExitCommand extends Command {

	public ExitCommand(CommandParameters params) {
		super(params);
	}

	public TranslationProcess execute(TranslationProcess process) {
		process.setRunning(false);
		return process;
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public Set<ConstraintViolation<CommandParameters>> getErrors() {
		return Collections.emptySet();
	}

}
