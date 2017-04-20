package com.example.dictionary;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class CommandParametersTest {

	@Test
	public void noAttributesCommand() {
		String command = "exit";
		CommandParameters params = CommandParameters.from(command);
		
		assertThat(params.command, is(command));
		assertThat(params.args.size(), equalTo(0));
	}
	
	@Test
	public void multipleAttributesCommand() {
		String command = "search book";
		CommandParameters params = CommandParameters.from(command);
		
		assertThat(params.command, is("search"));
		assertThat(params.args.size(), equalTo(1));
		assertThat(params.args.first(), is("book"));
	}
	
	
}
