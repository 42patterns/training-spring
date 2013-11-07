package com.example.dictionary;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class CommandParametersTest {

	@Test
	public void noAttributesCommand() {
		String command = "exit";
		CommandParameters params = new CommandParameters(command);
		
		assertThat(params.getCommandName(), is(command));
		assertThat(params.getAttributes().length, equalTo(0));
	}
	
	@Test
	public void multipleAttributesCommand() {
		String command = "search book";
		CommandParameters params = new CommandParameters(command);
		
		assertThat(params.getCommandName(), is("search"));
		assertThat(params.getAttributes().length, equalTo(1));
		assertThat(params.getAttributes()[0], is("book"));
	}
	
	
}
