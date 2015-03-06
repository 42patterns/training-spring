package com.example.dictionary.validation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ TYPE, METHOD, FIELD, PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = SearchParamatersValidator.class)
public @interface ValidSearchParameters {
	String message() default "{com.example.dictionary.validation.ValidSearchParameters.message}";
	
	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
