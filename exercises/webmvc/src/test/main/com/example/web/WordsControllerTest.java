package com.example.web;

import com.example.dictionary.model.DictionaryWord;
import com.example.dictionary.translation.TranslationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.ModelAndViewAssert.*;

public class WordsControllerTest {

    @Test
    public void should_return_words_view() {
        //given
        final List<DictionaryWord> translations = Arrays
                .asList(DictionaryWord.fromPolishWord("dom").withEnglishWord("house").build());

        WordsController controller = new WordsController();
        controller.service = mock(TranslationService.class);
        when(controller.service.getTranslationsForWord("dom"))
                .thenReturn(translations);

        //when
        ModelAndView mav = controller.htmlOutputSearch("dom");

        //then
        assertViewName(mav, "words");
        assertModelAttributeAvailable(mav, "translations");
        assertModelAttributeValue(mav, "translations", translations);
    }

}
