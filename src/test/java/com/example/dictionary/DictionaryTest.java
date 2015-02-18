package com.example.dictionary;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class DictionaryTest {

    Dictionary dict = new Dictionary();

    @Test
    public void should_return_24_translations_for_pole() throws IOException {
        List<DictionaryWord> list = dict.getTranslations("pole");

        System.out.println("pole = " + list);

        assertThat(list, is(notNullValue()));
        assertThat(list.size(), is(equalTo(24)));
        assertThat(list.get(0).getEnglishWord(), is(equalTo("area")));
    }

}
