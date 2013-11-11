package com.example.dictionary.commands;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.dictionary.CommandParameters;
import com.example.dictionary.model.TranslationProcess;
import com.example.dictionary.validator.NotValidCommand;

@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
@NotValidCommand
public class VoidCommand extends Command {

	@Autowired
	Validator validator;
	
	public VoidCommand(CommandParameters params) {
		super(params);
	}

	@Override
	public Set<ConstraintViolation<?>> getErrors() {
		Set<ConstraintViolation<VoidCommand>> errors = validator.validate(this);
		return new HashSet<ConstraintViolation<?>>(errors);
	}
	
	@Override
	public TranslationProcess execute(TranslationProcess process) {
		return process;
	}

}
