package com.example.dictionary.translation;

import com.example.dictionary.model.DictionaryWord;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class TranslationService {
    private static Logger log = Logger.getLogger(TranslationService.class);

    public TranslationService() {
    }

    public TranslationService(String urlStringTemplate) {
        this.urlStringTemplate = urlStringTemplate;
    }

    @Value("${urlStringTemplate}")
    private String urlStringTemplate;

    public List<DictionaryWord> getTranslationsForWord(String wordToTranslate) {
        List<String> words = getWords(wordToTranslate);

        return IntStream.range(0, words.size())                             //for all words in list
                .filter(i -> i % 2 == 0)                                    //take every second
                .mapToObj(i -> DictionaryWord.fromPolishWord(words.get(i))
                        .withEnglishWord(words.get(i + 1))
                        .build())                                           //map to domain object
                .collect(Collectors.toList());                              //end return
    }

    private List<String> getWords(String wordToFind) {
        String urlString = urlStringTemplate.replace("{}", wordToFind);
        log.info("URL: " + urlString);

        try {
            Pattern pat = Pattern
                    .compile(".*<a href=\"dict\\?words?=(.*)&lang.*");

            URL url = new URL(urlString);
            String s = new Scanner(url.openStream()).useDelimiter("\\A").next();
            return Arrays.asList(s.split("\n")).stream()
                .filter(l -> pat.asPredicate().test(l))
                    .map(l -> {
                        Matcher m = pat.matcher(l);
                        m.find();
                        return m.group(m.groupCount());
                    })
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}