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
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class VoidCommand extends Command {

	public VoidCommand(CommandParameters params) {
		super(params);
	}

	@Override
	public Set<ConstraintViolation<?>> getErrors() {
		return Collections.emptySet();
	}

	@Override
	public TranslationProcess execute(TranslationProcess process) {
		return process;
	}

}
