package com.example.dictionary.commands;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.dictionary.TranslationProcess;
import com.example.dictionary.validator.NotValidCommand;

@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
@NotValidCommand
public class VoidCommand extends Command {

	@Autowired
	Validator validator;
	
	public VoidCommand(TranslationProcess process) {
		super(process);
	}

	@Override
	public Set<ConstraintViolation<? extends Command>> getErrors() {
		Set<ConstraintViolation<VoidCommand>> errors = validator.validate(this);
		return new HashSet<ConstraintViolation<? extends Command>>(errors);
	}
	
	@Override
	public TranslationProcess execute() {
		return process;
	}

}
