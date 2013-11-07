package com.example.dictionary;

import com.example.dictionary.model.TranslationProcess;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import java.util.Scanner;
import java.util.Set;

@Component
public class Controller {

	@Autowired
	TranslationService transations;

	@Autowired
	BeanFactory factory;
	
	public void run() {
		boolean ok = true;
		Scanner s = new Scanner(System.in);
		TranslationProcess process = factory.getBean(TranslationProcess.class);
		
		while (process.isRunning()) {
			System.out.print("dictionary > ");
			String command = s.nextLine();

			CommandParameters params = new CommandParameters(command);
			process.setParams(params);
			
			if ("exit".equals(params.getCommandName())) {
				process.setRunning(false);
			} else if ("search".equals(params.getCommandName())) {
				Set<ConstraintViolation<CommandParameters>> errors = transations.validate(params);
				if (!errors.isEmpty()) {
					for (ConstraintViolation<CommandParameters> e: errors) {
						System.out.println("Field [name="+e.getPropertyPath()+"]: "+e.getMessage());
					}
					continue;
				}

				process = (TranslationProcess) factory.getBean("translationProcess", params);
				process = transations.getDictionaryWords(process);
			} else if ("show-all".equals(params.getCommandName())) {
				process.showAllWords();
			} else if ("show-saved".equals(params.getCommandName())) {
				process.printSavedWords();
			} else if ("save".equals(params.getCommandName())) {
				process.saveWord();
			}
		}
		s.close();
	}
	
}
