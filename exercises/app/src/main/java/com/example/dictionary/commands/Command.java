package com.example.dictionary.commands;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;

import com.example.dictionary.CommandParameters;
import com.example.dictionary.TranslationProcess;

public abstract class Command {

	@Valid
	protected TranslationProcess process;
	
	public Command(TranslationProcess process) {
		this.process = process;
	}

	public TranslationProcess getProcess() {
		return process;
	}

	public CommandParameters getParams() {
		return process.getParams();
	}

	public boolean isValid() {
		return getErrors().isEmpty();
	}
	
	public void printErrorInformation() {
		for (ConstraintViolation<?> e: getErrors()) {
			System.out.println("Field [name="+e.getPropertyPath()+"]: "+e.getMessage());
		}
	}
	
	abstract public Set<ConstraintViolation<? extends Command>> getErrors();
	abstract public TranslationProcess execute();
	
}
