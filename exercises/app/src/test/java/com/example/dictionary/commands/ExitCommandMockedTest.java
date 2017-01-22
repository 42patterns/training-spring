package com.example.dictionary.commands;

import com.example.dictionary.TranslationProcess;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ExitCommandMockedTest {

    @Mock
    TranslationProcess process;

    @InjectMocks
    ExitCommand command;

    @Test
    public void should_stop_running() {
        TranslationProcess process = command.execute();

        assertThat(process.isRunning(), CoreMatchers.is(equalTo(false)));
        Mockito.verify(process).setRunning(false);
    }

}