package com.example.dictionary.commands;

import java.util.Collections;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.dictionary.CommandParameters;
import com.example.dictionary.model.TranslationProcess;
import com.example.dictionary.repositories.Repository;

@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class ShowSavedWordsCommand extends Command {

	@Autowired
	@Qualifier("jpa")
	Repository repository;

	public ShowSavedWordsCommand(CommandParameters params) {
		super(params);
	}

	public TranslationProcess execute(TranslationProcess process) {
		repository.printSavedWords();
		return process;
	}

	@Override
	public Set<ConstraintViolation<?>> getErrors() {
		return Collections.emptySet();
	}

}
