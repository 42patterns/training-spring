package com.example.dictionary.commands;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.dictionary.CommandParameters;
import com.example.dictionary.TranslationProcess;

@Component
public class CommandFactory {

	@Autowired
	BeanFactory factory;
	
	public Command getCommand(TranslationProcess process) {
		CommandParameters params = process.getParams();
		if ("exit".equals(params.getCommandName())) {
			return (ExitCommand) factory.getBean("exitCommand", process);
		} else if ("search".equals(params.getCommandName())) {
			return (TranslationCommand) factory.getBean("translationCommand", process); 
		} else if ("show-all".equals(params.getCommandName())) {
			return (ShowAllWordsCommand) factory.getBean("showAllWordsCommand", process);
		} else if ("show-saved".equals(params.getCommandName())) {
			return (ShowSavedWordsCommand) factory.getBean("showSavedWordsCommand", process);
		} else if ("save".equals(params.getCommandName())) {
			return (SaveWordsCommand) factory.getBean("saveWordsCommand", process);
		}
		
		return (VoidCommand) factory.getBean("voidCommand", process);
	}

}
