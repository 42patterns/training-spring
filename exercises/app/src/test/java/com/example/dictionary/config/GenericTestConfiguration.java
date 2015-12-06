package com.example.dictionary.config;

import com.example.dictionary.TranslationProcess;
import com.example.dictionary.commands.TranslationCommand;
import com.example.dictionary.file.FileService;
import com.example.dictionary.repositories.InMemoryRepository;
import com.example.dictionary.repositories.Repository;
import com.example.dictionary.translation.TranslationService;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Profile("test")
public class GenericTestConfiguration {

	@Bean
	public Repository repository() {
		return new InMemoryRepository();
	}

    @Bean
    public PlatformTransactionManager txManager() {
        return Mockito.mock(PlatformTransactionManager.class);
    }
}
