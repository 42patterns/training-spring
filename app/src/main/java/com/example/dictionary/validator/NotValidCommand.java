package com.example.dictionary.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotValidCommandValidator.class)
public @interface NotValidCommand {

	String message() default "{com.example.dictionary.validator.NotValidCommand.message}";
	
	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
