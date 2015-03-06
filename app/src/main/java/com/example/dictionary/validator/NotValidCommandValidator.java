package com.example.dictionary.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.dictionary.commands.VoidCommand;

public class NotValidCommandValidator implements ConstraintValidator<NotValidCommand, VoidCommand> {

	@Override
	public void initialize(NotValidCommand constraintAnnotation) {
	}

	@Override
	public boolean isValid(VoidCommand value, ConstraintValidatorContext context) {
		return false;
	}

}
