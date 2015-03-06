package com.example.dictionary.repositories;

import com.example.dictionary.model.DictionaryWord;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Profile("hibernate")
public class HibernateRepository extends Repository {

	@Autowired
	SessionFactory mySessionFactory;

	public List<DictionaryWord> getSavedWords() {
		return mySessionFactory.openSession().getNamedQuery(DictionaryWord.GET_ALL_WORD)
				.list();
	}

	@Transactional
	public void addWord(DictionaryWord word) {
		mySessionFactory.getCurrentSession().persist(word);
		mySessionFactory.getCurrentSession().flush();
	}

}
