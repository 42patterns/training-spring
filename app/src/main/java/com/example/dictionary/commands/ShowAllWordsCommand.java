package com.example.dictionary.commands;

import com.example.dictionary.TranslationProcess;
import com.example.dictionary.model.DictionaryWord;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import java.util.Collections;
import java.util.Set;

@Component
@Scope(value=BeanDefinition.SCOPE_PROTOTYPE)
public class ShowAllWordsCommand extends Command {

	public ShowAllWordsCommand(TranslationProcess process) {
		super(process);
	}

	public TranslationProcess execute() {
        process.getWords().forEach((word) -> {
            int i = process.getWords().indexOf(word);
            System.out.println(i + ") " + word.getPolishWord() + " :: " + word.getEnglishWord());
        });
		return process;
	}

	@Override
	public Set<ConstraintViolation<? extends Command>> getErrors() {
		return Collections.emptySet();
	}
	
}
