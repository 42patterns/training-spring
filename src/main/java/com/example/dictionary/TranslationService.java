package com.example.dictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.example.dictionary.model.DictionaryWord;

@Component
public class TranslationService {
	private static Logger log = Logger.getLogger(TranslationService.class);

	@Value("${urlStringTemplate}")
	private String urlStringTemplate;

	private BufferedReader bufferedReader;

	public List<DictionaryWord> getDictionaryWords(String command) {
		Iterator<String> iterator = getWords(command).iterator();
		List<DictionaryWord> words = new ArrayList<DictionaryWord>();
		
		while (iterator.hasNext()) {
			DictionaryWord word = DictionaryWord.fromPolishWord(iterator.next())
				.withEnglishWord(iterator.next())
				.build();
			
			words.add(word);
		}
		
		return words;
	}
	
	public List<String> getWords(String command) {
		List<String> words = new ArrayList<String>();
		prepareBufferedReader(command);
		
		String word = moveToNextWord();
		while (hasNext(word)) {
			words.add(word);
			word = moveToNextWord();
		}
		dispose();
		
		return words;
	}

	private void prepareBufferedReader(String command) {
		try {
			String[] commandParts = command.split(" ");
			String wordToFind = commandParts[1];
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
