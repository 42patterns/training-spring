package com.example.dictionary;

import com.example.dictionary.file.FileRollbackHandler;
import com.example.dictionary.file.FileService;
import com.example.dictionary.model.DictionaryWord;
import com.example.dictionary.repositories.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

@Component
public class Controller {

	@Autowired
	TranslationService transations;

	@Autowired
	@Qualifier("hibernate")
	Repository repository;

    @Autowired
    FileService fileService;

    TransactionTemplate transactionTemplate;
	List<DictionaryWord> foundWords = new ArrayList<DictionaryWord>();

    @Autowired
    public Controller(PlatformTransactionManager txManager) {
        transactionTemplate = new TransactionTemplate(txManager);
    }

	public void run() {
		boolean ok = true;
		Scanner s = new Scanner(System.in);
		while (ok) {
			System.out.print("dictionary > ");
			String command = s.nextLine();
			
			CommandParameters params = new CommandParameters(command);
			
			if ("exit".equals(params.getCommandName())) {
				ok = false;
			} else if ("search".equals(params.getCommandName())) {
				Set<ConstraintViolation<CommandParameters>> errors = transations.validate(params);
				if (!errors.isEmpty()) {
					for (ConstraintViolation<CommandParameters> e: errors) {
						System.out.println("Field [name="+e.getPropertyPath()+"]: "+e.getMessage());
					}
					continue;
				}
				
				foundWords = transations.getDictionaryWords(params);
			} else if ("show-all".equals(params.getCommandName())) {
				for (int i = 0; i<foundWords.size(); i++) {
					DictionaryWord word = foundWords.get(i);
					System.out.println(i + ") " + word.getPolishWord() + " :: " + word.getEnglishWord());
				}
			} else if ("show-saved".equals(params.getCommandName())) {
				repository.printSavedWords();
			} else if ("save".equals(params.getCommandName())) {
				final Integer i = Integer.valueOf(params.getAttributes()[0]);

                transactionTemplate.execute(new TransactionCallback<Void>() {
                    @Override
                    public Void doInTransaction(TransactionStatus status) {

                        DictionaryWord word = foundWords.get(i);
                        String filename = fileService.createFile(word.toString());
                        TransactionSynchronizationManager.registerSynchronization(new FileRollbackHandler(filename));
                        repository.addWord(word);

                        return null;  //To change body of implemented methods use File | Settings | File Templates.
                    }
                });
			}
		}
		s.close();
	}
	
}
