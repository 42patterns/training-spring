package com.example.dictionary.repositories;

import com.example.dictionary.model.DictionaryWord;

import java.util.List;

public abstract class Repository {

	abstract public List<DictionaryWord> getSavedWords();

	abstract public void addWord(DictionaryWord word);

	public void printSavedWords() {
		List<DictionaryWord> savedWords = getSavedWords();
		for (int i = 0; i<savedWords.size(); i++) {
			DictionaryWord word = savedWords.get(i);
			System.out.println(i + ") " + word.getPolishWord() + " :: " + word.getEnglishWord());
		}
	}

}