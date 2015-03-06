package com.example.dictionary.repositories;

import com.example.dictionary.model.DictionaryWord;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
@Profile("jpa")
public class JpaRepository extends Repository {

	@PersistenceContext
	EntityManager em;
	
	public List<DictionaryWord> getSavedWords() {
		return em.createNamedQuery(DictionaryWord.GET_ALL_WORD, DictionaryWord.class)
				.getResultList();
	}

	@Transactional
	public void addWord(DictionaryWord word) {
		em.persist(word);
	}

}
