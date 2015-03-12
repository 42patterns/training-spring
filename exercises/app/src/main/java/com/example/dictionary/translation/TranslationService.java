package com.example.dictionary.translation;

import com.example.dictionary.model.DictionaryWord;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TranslationService {
    private static Logger log = Logger.getLogger(TranslationService.class);

    @Value("${urlStringTemplate}")
    private String urlStringTemplate;

    private BufferedReader bufferedReader;

    public List<DictionaryWord> getTranslationsForWord(String wordToTranslate) {
        Iterator<String> iterator = getWords(wordToTranslate).iterator();
        List<DictionaryWord> words = new ArrayList<>();

        while (iterator.hasNext()) {
            DictionaryWord word = DictionaryWord.fromPolishWord(iterator.next())
                    .withEnglishWord(iterator.next())
                    .build();

            words.add(word);
        }

        return words;
    }

    private List<String> getWords(String wordToFind) {
        List<String> words = new ArrayList<>();
        prepareBufferedReader(wordToFind);

        String word = moveToNextWord();
        while (hasNext(word)) {
            words.add(word);
            word = moveToNextWord();
        }
        dispose();

        return words;
    }


    private void prepareBufferedReader(String wordToFind) {
        try {
            String urlString = urlStringTemplate.replace("{}", wordToFind);
            log.info("URL: " + urlString);

            bufferedReader = new BufferedReader(new InputStreamReader(new URL(
                    urlString).openStream()));
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
