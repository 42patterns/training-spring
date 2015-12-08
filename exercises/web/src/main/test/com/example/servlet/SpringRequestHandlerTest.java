package com.example.servlet;

import com.example.dictionary.translation.TranslationService;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class SpringRequestHandlerTest {

    @Test
    public void should_get_translations_and_forward_to_view() throws ServletException, IOException {

        SpringRequestHandler handler = new SpringRequestHandler();
        handler.service = new TranslationService("http://www.dict.pl/dict?word={}&words=&lang=PL");

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("word", "dom");
        MockHttpServletResponse response = new MockHttpServletResponse();

        handler.handleRequest(request, response);

        assertThat(request.getAttribute("translations"), notNullValue());
    }

}
