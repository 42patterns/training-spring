package com.example.servlet;

import com.example.dictionary.model.DictionaryWord;
import com.example.dictionary.translation.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Component("myRequestHandler")
public class SpringRequestHandler implements HttpRequestHandler {

    @Autowired
    TranslationService service;

    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String word = request.getParameter("word");
        List<DictionaryWord> translations = service.getTranslationsForWord(word);

        request.setAttribute("translations", translations);
        request.getRequestDispatcher("/words.jsp").forward(request, response);
    }
}
