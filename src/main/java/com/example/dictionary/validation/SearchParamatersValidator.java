package com.example.dictionary.validation;

import com.example.dictionary.TranslationProcess;
import com.example.dictionary.commands.Command;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class SearchParamatersValidator implements
		ConstraintValidator<ValidSearchParameters, Command> {

	
	Pattern pattern = Pattern.compile("[0-9]+");
	
	@Override
	public void initialize(ValidSearchParameters constraintAnnotation) {

	}

	@Override
	public boolean isValid(Command command, ConstraintValidatorContext context) {
		TranslationProcess process = command.getProcess();

		if (process.getWords() == null) {
            return false;
        }

		if (!pattern.matcher(command.getParams().getAttributes()[0]).matches()) {
            return false;
        }
		
		Integer saveIndex = Integer.valueOf(command.getParams().getAttributes()[0]);
		
		if (saveIndex > (process.getWords().size() - 1)) {
            return false;
        }
		
		return true;
	}

}
