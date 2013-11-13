package com.example.dictionary.commands;

import java.util.Collections;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.dictionary.TranslationProcess;
import com.example.dictionary.model.DictionaryWord;

@Component
@Scope(value=BeanDefinition.SCOPE_PROTOTYPE)
public class ShowAllWordsCommand extends Command {

	public ShowAllWordsCommand(TranslationProcess process) {
		super(process);
	}

	public TranslationProcess execute() {
		for (int i = 0; i<process.getWords().size(); i++) {
			DictionaryWord word = process.getWords().get(i);
			System.out.println(i + ") " + word.getPolishWord() + " :: " + word.getEnglishWord());
		}
		return process;
	}

	@Override
	public Set<ConstraintViolation<? extends Command>> getErrors() {
		return Collections.emptySet();
	}
	
}
