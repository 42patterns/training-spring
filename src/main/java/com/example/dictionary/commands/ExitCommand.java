package com.example.dictionary.commands;

import java.util.Collections;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.dictionary.TranslationProcess;

@Component
@Scope(value=BeanDefinition.SCOPE_PROTOTYPE)
public class ExitCommand extends Command {

	public ExitCommand(TranslationProcess process) {
		super(process);
	}

	public TranslationProcess execute() {
		process.setRunning(false);
		return process;
	}

	@Override
	public Set<ConstraintViolation<? extends Command>> getErrors() {
		return Collections.emptySet();
	}

}
