package com.example.dictionary.repositories;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.example.dictionary.model.DictionaryWord;

@Component
@Qualifier("hibernate")
public class HibernateRepository extends Repository {

	@Autowired
	SessionFactory mySessionFactory;

	public List<DictionaryWord> getSavedWords() {
		return mySessionFactory.openSession().getNamedQuery(DictionaryWord.GET_ALL_WORD)
				.list();
	}

	public void addWord(DictionaryWord word) {
		//..
	}

}
