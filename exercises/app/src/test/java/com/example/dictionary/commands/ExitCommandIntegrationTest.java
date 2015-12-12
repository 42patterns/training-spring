package com.example.dictionary.commands;

import com.example.AppConfiguration;
import com.example.dictionary.CommandParameters;
import com.example.dictionary.TranslationProcess;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CommandFactory.class, ExitCommand.class})
public class ExitCommandIntegrationTest {

    @Autowired
    CommandFactory factory;

    @Test
    public void should_stop_running() {
        //given
        Command command = factory.getCommand(TranslationProcess.fromCommandParameters(new CommandParameters("exit")));

        //when
        TranslationProcess process = command.execute();

        //then
        assertThat(process.isRunning(), CoreMatchers.is(equalTo(false)));
    }

}