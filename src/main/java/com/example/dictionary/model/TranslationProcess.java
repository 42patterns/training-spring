package com.example.dictionary.model;

import com.example.dictionary.CommandParameters;
import com.example.dictionary.file.FileRollbackHandler;
import com.example.dictionary.file.FileService;
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

import java.util.List;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class TranslationProcess {

    @Autowired
    @Qualifier("jpa")
    Repository repository;

    @Autowired
    FileService fileService;

    @Autowired
    @Qualifier("jpaTxMgr")
    PlatformTransactionManager txManager;

    private List<DictionaryWord> words;
    private CommandParameters params;

    public TranslationProcess() {
    }

    public TranslationProcess(CommandParameters params) {
        this.params = params;
    }

    public CommandParameters getParams() {
        return params;
    }

    public void setParams(CommandParameters params) {
        this.params = params;
    }

    public List<DictionaryWord> getWords() {
        return words;
    }

    public void setWords(List<DictionaryWord> words) {
        this.words = words;
    }

    public void saveWord() {
        final Integer i = Integer.valueOf(params.getAttributes()[0]);

        TransactionTemplate transactionTemplate = new TransactionTemplate(txManager);
        transactionTemplate.execute(new TransactionCallback<Void>() {
            @Override
            public Void doInTransaction(TransactionStatus status) {

                DictionaryWord word = words.get(i);
                String filename = fileService.createFile(word.toString());
                TransactionSynchronizationManager.registerSynchronization(new FileRollbackHandler(filename));
                repository.addWord(word);

                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }

    public void printSavedWords() {
        repository.printSavedWords();
    }

    public void showAllWords() {
        for (int i = 0; i < words.size(); i++) {
            DictionaryWord word = words.get(i);
            System.out.println(i + ") " + word.getPolishWord() + " :: " + word.getEnglishWord());
        }
    }

}
