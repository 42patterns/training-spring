package com.example.dictionary.commands;

import java.util.Collections;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.dictionary.CommandParameters;
import com.example.dictionary.model.DictionaryWord;
import com.example.dictionary.model.TranslationProcess;

@Component
@Scope(value=BeanDefinition.SCOPE_PROTOTYPE)
public class ShowAllWordsCommand extends Command {

	public ShowAllWordsCommand(CommandParameters params) {
		super(params);
	}

	public TranslationProcess execute(TranslationProcess process) {
		for (int i = 0; i<process.getWords().size(); i++) {
			DictionaryWord word = process.getWords().get(i);
			System.out.println(i + ") " + word.getPolishWord() + " :: " + word.getEnglishWord());
		}
		return process;
	}

	@Override
	public Set<ConstraintViolation<CommandParameters>> getErrors() {
		return Collections.emptySet();
	}
	
}
