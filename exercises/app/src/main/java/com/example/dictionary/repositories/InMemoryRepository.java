package com.example.dictionary.repositories;

import com.example.dictionary.model.DictionaryWord;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryRepository extends Repository {

	List<DictionaryWord> savedWords = new ArrayList<>();
	
	public List<DictionaryWord> getSavedWords() {
		return new ArrayList<>(savedWords);
	}
	
	public void addWord(DictionaryWord word) {
		savedWords.add(word);
	}

}
