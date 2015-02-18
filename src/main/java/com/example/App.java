package com.example;

import com.example.dictionary.Dictionary;
import com.example.dictionary.DictionaryWord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@EnableAutoConfiguration
@RestController
@ComponentScan
public class App {

    private Dictionary dict;

    @Autowired
    public App(Dictionary dict) {
        this.dict = dict;
    }

    @RequestMapping("/{word}")
    public List<DictionaryWord> getTranslations(@PathVariable String word) throws IOException {
        return dict.getTranslations(word);
    }


    public static void main(String... args) {
        SpringApplication.run(App.class, args);
    }
}
