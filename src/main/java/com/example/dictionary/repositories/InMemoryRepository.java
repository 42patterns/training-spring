package com.example.dictionary.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.example.dictionary.model.DictionaryWord;

@Component
@Qualifier("inmemory")
public class InMemoryRepository extends Repository {

	List<DictionaryWord> savedWords = new ArrayList<DictionaryWord>();
	
	public List<DictionaryWord> getSavedWords() {
		return new ArrayList<DictionaryWord>(savedWords);
	}
	
	public void addWord(DictionaryWord word) {
		savedWords.add(word);
	}

}
