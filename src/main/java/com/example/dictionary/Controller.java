package com.example.dictionary;

import java.util.List;
import java.util.Scanner;

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
			
			if ("exit".equals(command)) {
				ok = false;
			} else if (command.startsWith("search")) {
				List<DictionaryWord> words = transations.getDictionaryWords(command);
				System.out.println(words);
			}
		}
		s.close();
	}
	
}
