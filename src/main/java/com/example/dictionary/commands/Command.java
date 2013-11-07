package com.example.dictionary.commands;

import java.util.Set;

import javax.validation.ConstraintViolation;

import com.example.dictionary.CommandParameters;
import com.example.dictionary.model.TranslationProcess;

public abstract class Command {

	protected CommandParameters params;
	
	public Command(CommandParameters params) {
		this.params = params;
	}
	
	
	public CommandParameters getParams() {
		return params;
	}

	public void printErrorInformation() {
		for (ConstraintViolation<CommandParameters> e: getErrors()) {
			System.out.println("Field [name="+e.getPropertyPath()+"]: "+e.getMessage());
		}
	}
	
	abstract public Set<ConstraintViolation<CommandParameters>> getErrors();
	abstract public TranslationProcess execute(TranslationProcess process);
	abstract public boolean isValid();
	
}
