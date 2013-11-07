package com.example.dictionary;

import com.example.dictionary.commands.Command;
import com.example.dictionary.commands.CommandFactory;
import com.example.dictionary.model.TranslationProcess;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Controller {

	@Autowired
	CommandFactory commandFactory;
	
	@Autowired
	BeanFactory factory;
	
	public void run() {
		boolean ok = true;
		Scanner s = new Scanner(System.in);
		TranslationProcess process = new TranslationProcess();
		
		while (process.isRunning()) {
			System.out.print("dictionary > ");
			String command = s.nextLine();

			Command c = commandFactory.getCommand(new CommandParameters(command));
			if (!c.isValid()) {
				c.printErrorInformation();
				continue;
			}
			
			process = c.execute(process);
		}
		s.close();
	}
	
}
