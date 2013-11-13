package com.example.dictionary.commands;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.dictionary.CommandParameters;
import com.example.dictionary.TranslationProcess;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class CommandFactoryTest {

	@Autowired
	CommandFactory commands;
	
	@Test
	public void voidCommand() {
		Command command = commands.getCommand(TranslationProcess.fromCommandParameters(new CommandParameters("eit")));
		assertThat(command, is(VoidCommand.class));
	}

	@Test
	public void searchCommand() {
		Command command = commands.getCommand(TranslationProcess.fromCommandParameters(new CommandParameters("search foo")));
		assertThat(command, is(TranslationCommand.class));
	}

	
	@Configuration
	@ComponentScan("com.example.dictionary.commands")
	public static class TestConfig {
		
	}
}
