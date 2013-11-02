package com.example.dictionary;

import java.util.Arrays;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.dictionary.validation.NoAttributesValidationGroup;
import com.example.dictionary.validation.SearchValidationGroup;

public class CommandParameters {

	@NotNull
	@Size(min=1)
	private String commandName;

	@Size.List({
		@Size(min=0, max=0, groups=NoAttributesValidationGroup.class),
		@Size(min=1, groups=SearchValidationGroup.class)
	})
	private String[] attributes;

	public CommandParameters(String command) {
		String[] commandParts = command.split(" ");
		this.commandName = commandParts[0];
		this.attributes = Arrays.copyOfRange(commandParts, 1, commandParts.length);
	}
	
	public String getCommandName() {
		return commandName;
	}

	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}

	public String[] getAttributes() {
		return attributes;
	}

	public void setAttributes(String[] attributes) {
		this.attributes = attributes;
	}

}
