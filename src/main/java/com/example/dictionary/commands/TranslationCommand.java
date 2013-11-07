package com.example.dictionary.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.example.dictionary.CommandParameters;
import com.example.dictionary.model.DictionaryWord;
import com.example.dictionary.model.TranslationProcess;
import com.example.dictionary.validation.SearchValidationGroup;

@Component
@Scope(value=BeanDefinition.SCOPE_PROTOTYPE)
public class TranslationCommand extends Command {

	private static Logger log = Logger.getLogger(TranslationCommand.class);

	@Value("${urlStringTemplate}")
	private String urlStringTemplate;

	@Autowired
	private Validator validator;
	
	private BufferedReader bufferedReader;

	public TranslationCommand() {
		super(new CommandParameters("search"));
	}

	public TranslationCommand(CommandParameters params) {
		super(params);
	}

	@Override
	public Set<ConstraintViolation<CommandParameters>> getErrors() {
		return validator.validate(params, SearchValidationGroup.class);
	}
	
	public TranslationProcess execute(TranslationProcess process) {
		Iterator<String> iterator = getWords(params).iterator();
		List<DictionaryWord> words = new ArrayList<DictionaryWord>();
		
		while (iterator.hasNext()) {
			DictionaryWord word = DictionaryWord.fromPolishWord(iterator.next())
				.withEnglishWord(iterator.next())
				.build();
			
			words.add(word);
		}
		
		process.setWords(words);
		return process;
	}
	
	private List<String> getWords(CommandParameters params) {
		List<String> words = new ArrayList<String>();
		prepareBufferedReader(params.getAttributes());
		
		String word = moveToNextWord();
		while (hasNext(word)) {
			words.add(word);
			word = moveToNextWord();
		}
		dispose();
		
		return words;
	}

	private void prepareBufferedReader(String[] commandAttributes) {
		try {
			String wordToFind = commandAttributes[0];
			String urlString = urlStringTemplate.replace("{}", wordToFind);
			log.info("URL: " + urlString);
			
			bufferedReader = new BufferedReader(new InputStreamReader(new URL(
					urlString).openStream()));
		} catch (MalformedURLException ex) {
			throw new RuntimeException(ex);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	private String moveToNextWord() {
		try {

			String line = bufferedReader.readLine();
			Pattern pat = Pattern
					.compile(".*<a href=\"dict\\?words?=(.*)&lang.*");

			while (hasNext(line)) {
				Matcher matcher = pat.matcher(line);
				if (matcher.find()) {
					return matcher.group(matcher.groupCount());
				} else {
					line = bufferedReader.readLine();
				}
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		return null;
	}
	
	private void dispose() {
		try {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	private boolean hasNext(String item) {
		return (item != null);
	}

}
