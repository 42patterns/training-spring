package com.example.web;

import com.example.dictionary.model.DictionaryWord;
import com.example.dictionary.repositories.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class WordsController {

    @Autowired
    Repository repo;

    @RequestMapping(value = "/saved-words", method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<DictionaryWord> main() {
        return repo.getSavedWords();
    }

}
