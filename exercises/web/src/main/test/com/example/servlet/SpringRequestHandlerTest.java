package com.example.servlet;

import com.example.dictionary.model.DictionaryWord;
import com.example.dictionary.translation.TranslationService;
import org.hamcrest.CoreMatchers;
import org.hamcrest.core.IsCollectionContaining;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SpringRequestHandlerTest {

    final List<DictionaryWord> translations = Arrays
            .asList(DictionaryWord.fromPolishWord("dom").withEnglishWord("house").build());

    @Test
    public void should_get_translations_and_forward_to_view() throws ServletException, IOException {

        SpringRequestHandler handler = new SpringRequestHandler();
        handler.service = mock(TranslationService.class);
        when(handler.service.getTranslationsForWord("dom"))
                .thenReturn(translations);

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("word", "dom");
        MockHttpServletResponse response = new MockHttpServletResponse();

        handler.handleRequest(request, response);

        assertThat(request.getAttribute("translations"), notNullValue());
        assertThat(request.getAttribute("translations"), is(equalTo(translations)));
    }

}
