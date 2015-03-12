package com.example.web;

import com.example.dictionary.model.DictionaryWord;
import com.example.dictionary.repositories.Repository;
import com.example.dictionary.translation.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Controller
public class WordsController {

    @Autowired
    Repository repo;

    @Autowired
    TranslationService service;

    @RequestMapping(value = "/show-saved", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<DictionaryWord> showSaved() {
        return repo.getSavedWords();
    }

    @RequestMapping(value = "/search/{word}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<DictionaryWord> search(@PathVariable String word) {
        return service.getTranslationsForWord(word);
    }

    @RequestMapping(value = "/search/{word}/{elementId}", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@PathVariable String word, @PathVariable String elementId) {
        List<DictionaryWord> translations = service.getTranslationsForWord(word);
        repo.addWord(translations.get(Integer.valueOf(elementId)));
    }

}
