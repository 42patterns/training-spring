package com.example.dictionary.commands;

import com.example.dictionary.CommandParameters;
import com.example.dictionary.TranslationProcess;
import com.example.dictionary.model.DictionaryWord;
import com.example.dictionary.translation.TranslationService;
import com.example.dictionary.validation.groups.SearchValidationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.ObjectMessage;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Scope(value=BeanDefinition.SCOPE_PROTOTYPE)
public class TranslationCommand extends Command {

	@Autowired
    private TranslationService service;

	@Autowired
	private Validator validator;

	@Autowired
	JmsTemplate jmsTemplate;

	public TranslationCommand(TranslationProcess process) {
		super(process);
	}

	@Override
	public Set<ConstraintViolation<? extends Command>> getErrors() {
		Set<ConstraintViolation<TranslationCommand>> errors = validator.validate(this, SearchValidationGroup.class);
		return new HashSet<>(errors);
	}
	
	public TranslationProcess execute() {
		String word = getFirstAttribute(getParams());

		jmsTemplate.send(session -> {
			ObjectMessage objectMessage = session.createObjectMessage(word);
			return objectMessage;
		});

		List<DictionaryWord> words = service.getTranslationsForWord(word);
		process.setWords(words);
		return process;
	}

    private String getFirstAttribute(CommandParameters params) {
        return params.getAttributes()[0];
    }


}
