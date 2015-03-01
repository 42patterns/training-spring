package com.example.dictionary.repositories;

import com.example.dictionary.model.DictionaryWord;

import java.util.List;

public abstract class Repository {

	abstract public List<DictionaryWord> getSavedWords();

	abstract public void addWord(DictionaryWord word);

	public void printSavedWords() {
		List<DictionaryWord> savedWords = getSavedWords();
        savedWords.forEach((word) -> {
            int i = savedWords.indexOf(word);
            System.out.println(i + ") " + word.getPolishWord() + " :: " + word.getEnglishWord());
        });
	}
}