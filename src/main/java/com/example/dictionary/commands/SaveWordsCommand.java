package com.example.dictionary.commands;

import com.example.dictionary.CommandParameters;
import com.example.dictionary.TranslationProcess;
import com.example.dictionary.file.FileRollbackHandler;
import com.example.dictionary.file.FileService;
import com.example.dictionary.groups.OnlyOneArgumentValidationGroup;
import com.example.dictionary.model.DictionaryWord;
import com.example.dictionary.repositories.Repository;
import com.example.dictionary.validation.ValidSearchParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.HashSet;
import java.util.Set;

@Component
@Scope(value=BeanDefinition.SCOPE_PROTOTYPE)
@ValidSearchParameters
public class SaveWordsCommand extends Command {


	@Autowired
	@Qualifier("jpa")
    Repository repository;

    @Autowired
    FileService fileService;

    @Autowired
    @Qualifier("jpaTxMgr")
    PlatformTransactionManager txManager;

    @Autowired
    Validator validator;

	public SaveWordsCommand() {
		super(TranslationProcess.fromCommandParameters(new CommandParameters("save")));
	}
	
	public SaveWordsCommand(TranslationProcess process) {
		super(process);
	}

    @Override
	public TranslationProcess execute() {
        final TranslationProcess process = this.process;
        final Integer i = Integer.valueOf(getParams().getAttributes()[0]);

        TransactionTemplate transactionTemplate = new TransactionTemplate(txManager);
        transactionTemplate.execute(status -> {

            DictionaryWord word = process.getWords().get(i);
            String filename = fileService.createFile(word.toString());
            TransactionSynchronizationManager.registerSynchronization(new FileRollbackHandler(filename));
            repository.addWord(word);
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        });
		return process;
	}

	@Override
	public Set<ConstraintViolation<? extends Command>> getErrors() {
		Set<ConstraintViolation<SaveWordsCommand>> errors = validator.validate(this, Default.class, OnlyOneArgumentValidationGroup.class);
		return new HashSet<>(errors);
	}
	
}
