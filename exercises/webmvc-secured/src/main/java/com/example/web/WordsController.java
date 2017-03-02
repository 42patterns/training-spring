package com.example.web;

import com.example.dictionary.model.DictionaryWord;
import com.example.dictionary.repositories.Repository;
import com.example.dictionary.translation.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.concurrent.CompletableFuture;

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
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseBody
    public DeferredResult<List<DictionaryWord>> search(@PathVariable String word) {
        DeferredResult<List<DictionaryWord>> result = new DeferredResult<>();

        CompletableFuture.supplyAsync(() -> service.getTranslationsForWord(word))
            .thenAccept(dictionaryWords -> result.setResult(dictionaryWords));

        return result;
//        return service.getTranslationsForWord(word);
    }

    @RequestMapping(value = "/search/{word}.html", method = RequestMethod.GET)
    public ModelAndView htmlOutputSearch(@PathVariable String word) {
        ModelAndView model = new ModelAndView("words");
        model.addObject("translations", service.getTranslationsForWord(word));
        return model;
    }

    @RequestMapping(value = "/search/{word}/{elementId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@PathVariable String word, @PathVariable String elementId) {
        List<DictionaryWord> translations = service.getTranslationsForWord(word);
        repo.addWord(translations.get(Integer.valueOf(elementId)));
    }

}
