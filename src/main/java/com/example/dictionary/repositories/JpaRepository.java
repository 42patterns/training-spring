package com.example.dictionary.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.example.dictionary.model.DictionaryWord;

@Component
@Qualifier("jpa")
public class JpaRepository extends Repository {

	@PersistenceContext
	EntityManager em;
	
	public List<DictionaryWord> getSavedWords() {
		return em.createNamedQuery(DictionaryWord.GET_ALL_WORD, DictionaryWord.class)
				.getResultList();
	}

	public void addWord(DictionaryWord word) {
		//..
	}

}
