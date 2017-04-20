package com.example.dictionary;

import com.example.dictionary.groups.OnlyOneArgumentValidationGroup;
import com.example.dictionary.validation.groups.NoAttributesValidationGroup;
import com.example.dictionary.validation.groups.SearchValidationGroup;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.List;

public class CommandParameters {

	@NotEmpty
	public final String command;

	@Valid
	public final Args args;

	private CommandParameters(String commandLine) {
		String[] split = commandLine.split(" ");
		this.command = split[0];
		this.args = new Args(Arrays.copyOfRange(split, 1, split.length));
	}

	public static CommandParameters from(String commandLine) {
		return new CommandParameters(commandLine);
	}

	public class Args {

		@NotNull(groups=SearchValidationGroup.class)
		@Size.List({
				@Size(min=0, max=0, groups=NoAttributesValidationGroup.class),
				@Size(min=1, groups=SearchValidationGroup.class),
				@Size(min=1, max=1, groups=OnlyOneArgumentValidationGroup.class)
		})
		final List<String> args;

		private Args(String[] a) {
			this.args = Arrays.asList(a);
		}

		public String first() {
			return args.iterator().next();
		}

		public int size() { return args.size(); }

		@Override
		public String toString() {
			return "Args" + args;
		}
	}
}
