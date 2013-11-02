package com.example.dictionary;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.dictionary.model.DictionaryWord;

@Component
public class Controller {

	@Autowired
	TranslationService transations;
	
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
				
				List<DictionaryWord> words = transations.getDictionaryWords(params);
				System.out.println(words);
			}
		}
		s.close();
	}
	
}
