package com.example.web;

import com.example.dictionary.model.DictionaryWord;
import com.example.dictionary.translation.TranslationService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.ModelAndViewAssert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class WordsControllerTest {

    final List<DictionaryWord> translations = Arrays
            .asList(DictionaryWord.fromPolishWord("dom").withEnglishWord("house").build());

    WordsController controller;

    @Before
    public void setup() {
        //given
        controller = new WordsController();
        controller.service = mock(TranslationService.class);
        when(controller.service.getTranslationsForWord("dom"))
                .thenReturn(translations);
    }

    @Test
    public void should_return_words_view() {
        //when
        ModelAndView mav = controller.htmlOutputSearch("dom");

        //then
        assertViewName(mav, "words");
        assertModelAttributeAvailable(mav, "translations");
        assertModelAttributeValue(mav, "translations", translations);
    }

    @Test
    public void test_jsp_output() throws Exception {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");

        standaloneSetup(controller).setViewResolvers(viewResolver).build()
                .perform(get("/search/dom.html"))
                .andExpect(status().isOk())
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("translations"))
                .andExpect(forwardedUrl("/WEB-INF/views/words.jsp"));
    }

    @Test
    public void test_json_output() throws Exception {
        standaloneSetup(controller).build()
                .perform(get("/search/dom").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].polishWord").value("dom"))
                .andExpect(jsonPath("$[0].englishWord").value("house"));
    }
}
