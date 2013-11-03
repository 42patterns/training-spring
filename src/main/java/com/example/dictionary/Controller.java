package com.example.dictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.example.dictionary.model.DictionaryWord;
import com.example.dictionary.repositories.Repository;

@Component
public class Controller {

	@Autowired
	TranslationService transations;

	@Autowired
	@Qualifier("datasource")
	Repository repository;
	
	List<DictionaryWord> foundWords = new ArrayList<DictionaryWord>();
	
	public void run() {
		boolean ok = true;
		Scanner s = new Scanner(System.in);
		while (ok) {
			System.out.print("dictionary > ");
			String command = s.nextLine();
			
			CommandParameters params = new CommandParameters(command);
			
			if ("exit".equals(params.getCommandName())) {
				ok = false;
			} else if ("search".equals(params.getCommandName())) {
				Set<ConstraintViolation<CommandParameters>> errors = transations.validate(params);
				if (!errors.isEmpty()) {
					for (ConstraintViolation<CommandParameters> e: errors) {
						System.out.println("Field [name="+e.getPropertyPath()+"]: "+e.getMessage());
					}
					continue;
				}
				
				foundWords = transations.getDictionaryWords(params);
			} else if ("show-all".equals(params.getCommandName())) {
				for (int i = 0; i<foundWords.size(); i++) {
					DictionaryWord word = foundWords.get(i);
					System.out.println(i + ") " + word.getPolishWord() + " :: " + word.getEnglishWord());
				}
			} else if ("show-saved".equals(params.getCommandName())) {
				repository.printSavedWords();
			} else if ("save".equals(params.getCommandName())) {
				Integer i = Integer.valueOf(params.getAttributes()[0]);
				repository.addWord(foundWords.get(i));
			}
		}
		s.close();
	}
	
}
