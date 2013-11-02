package com.example.dictionary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.example.dictionary.TranslationService;

public class GenericTestConfiguration {

	@Bean
	public LocalValidatorFactoryBean validator() {
		return new LocalValidatorFactoryBean();
	}

	@Bean
	public TranslationService service() {
		return new TranslationService();
	}

}
