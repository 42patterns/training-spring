package com.example.dictionary.commands;

import com.example.dictionary.TranslationProcess;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class ExitCommandTest {

    @Test
    public void should_stop_running() {
        //given
        ExitCommand command = new ExitCommand(new TranslationProcess());

        //when
        TranslationProcess process = command.execute();

        //then
        assertThat(process.isRunning(), CoreMatchers.is(equalTo(false)));
    }

}