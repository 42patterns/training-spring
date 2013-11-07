package com.example.dictionary.model;

import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.dictionary.CommandParameters;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TranslationProcess {

	private List<DictionaryWord> words;
	private CommandParameters params;

	public TranslationProcess() {
	}

	public TranslationProcess(CommandParameters params) {
		this.params = params;
	}

	public CommandParameters getParams() {
		return params;
	}

	public void setParams(CommandParameters params) {
		this.params = params;
	}

	public List<DictionaryWord> getWords() {
		return words;
	}

	public void setWords(List<DictionaryWord> words) {
		this.words = words;
	}

}
