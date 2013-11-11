package com.example.dictionary.commands;

import com.example.dictionary.CommandParameters;
import com.example.dictionary.file.FileRollbackHandler;
import com.example.dictionary.file.FileService;
import com.example.dictionary.model.DictionaryWord;
import com.example.dictionary.model.TranslationProcess;
import com.example.dictionary.repositories.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.validation.ConstraintViolation;
import java.util.Collections;
import java.util.Set;

@Component
@Scope(value=BeanDefinition.SCOPE_PROTOTYPE)
public class SaveWordsCommand extends Command {


	@Autowired
	@Qualifier("jpa")
	Repository repository;

    @Autowired
    FileService fileService;

    @Autowired
    @Qualifier("jpaTxMgr")
    PlatformTransactionManager txManager;

	public SaveWordsCommand(CommandParameters params) {
		super(params);
	}

	public TranslationProcess execute(final TranslationProcess process) {
        final Integer i = Integer.valueOf(getParams().getAttributes()[0]);

        TransactionTemplate transactionTemplate = new TransactionTemplate(txManager);
        transactionTemplate.execute(new TransactionCallback<Void>() {
            @Override
            public Void doInTransaction(TransactionStatus status) {

                DictionaryWord word = process.getWords().get(i);
                String filename = fileService.createFile(word.toString());
                TransactionSynchronizationManager.registerSynchronization(new FileRollbackHandler(filename));
                repository.addWord(word);

                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
		return process;
	}

	@Override
	public Set<ConstraintViolation<?>> getErrors() {
		return Collections.emptySet();
	}
	
}
