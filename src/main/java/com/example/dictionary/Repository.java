package com.example.dictionary;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.dictionary.model.DictionaryWord;

@Component
public class Repository {

	List<DictionaryWord> savedWords = new ArrayList<DictionaryWord>();
	
	public List<DictionaryWord> getSavedWords() {
		return new ArrayList<DictionaryWord>(savedWords);
	}
	
	public void addWord(DictionaryWord word) {
		savedWords.add(word);
	}

	public void printSavedWords() {
		for (int i = 0; i<savedWords.size(); i++) {
			DictionaryWord word = savedWords.get(i);
			System.out.println(i + ") " + word.getPolishWord() + " :: " + word.getEnglishWord());
		}
	}
}
