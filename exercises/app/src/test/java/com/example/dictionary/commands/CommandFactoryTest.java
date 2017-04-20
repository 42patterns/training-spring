package com.example.dictionary.commands;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import com.example.AppConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.dictionary.CommandParameters;
import com.example.dictionary.TranslationProcess;

@ContextConfiguration(classes = AppConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class CommandFactoryTest {

	@Autowired
	CommandFactory commands;
	
	@Test
	public void voidCommand() {
		Command command = commands.getCommand(TranslationProcess.fromCommandParameters(CommandParameters.from("eit")));
		assertThat(command, instanceOf(VoidCommand.class));
	}

	@Test
	public void searchCommand() {
		Command command = commands.getCommand(TranslationProcess.fromCommandParameters(CommandParameters.from("search foo")));
		assertThat(command, instanceOf(TranslationCommand.class));
	}

}
