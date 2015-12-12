package com.example.dictionary;

import com.example.dictionary.model.DictionaryWord;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class TranslationProcess {

	@Valid
	@NotNull
	private CommandParameters params;
	private List<DictionaryWord> words = new ArrayList<>();
	private boolean running = true;
	
	public List<DictionaryWord> getWords() {
		return words;
	}

	public void setWords(List<DictionaryWord> words) {
		this.words = words;
	}

	public CommandParameters getParams() {
		return params;
	}

	public void setParams(CommandParameters params) {
		this.params = params;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
	
	public static TranslationProcess fromCommandParameters(CommandParameters params) {
		TranslationProcess p = new TranslationProcess();
		p.setParams(params);
		return p;
	}

	public static TranslationProcess fromCommandParameters(String strParams) {
		TranslationProcess p = new TranslationProcess();
		p.setParams(new CommandParameters(strParams));
		return p;
	}

}
