package com.example.dictionary.commands;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.dictionary.CommandParameters;

@Component
public class CommandFactory {

	@Autowired
	BeanFactory factory;
	
	public Command getCommand(CommandParameters params) {
		if ("exit".equals(params.getCommandName())) {
			return (ExitCommand) factory.getBean("exitCommand", params);
		} else if ("search".equals(params.getCommandName())) {
			return (TranslationCommand) factory.getBean("translationCommand", params); 
		} else if ("show-all".equals(params.getCommandName())) {
			return (ShowAllWordsCommand) factory.getBean("showAllWordsCommand", params);
		} else if ("show-saved".equals(params.getCommandName())) {
			return (ShowSavedWordsCommand) factory.getBean("showSavedWordsCommand", params);
		} else if ("save".equals(params.getCommandName())) {
			return (SaveWordsCommand) factory.getBean("saveWordsCommand", params);
		}
		
		throw new IllegalArgumentException("Command not found");
	}

}
