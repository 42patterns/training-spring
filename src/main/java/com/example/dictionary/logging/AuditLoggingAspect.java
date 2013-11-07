package com.example.dictionary.logging;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.example.dictionary.commands.TranslationCommand;

@Component
@Aspect
public class AuditLoggingAspect {

	private static Logger log = Logger.getLogger(AuditLoggingAspect.class);
	
	@Before("execution(* com.example.dictionary.commands.TranslationCommand.execute(*))")
	public void logWebServiceCall(JoinPoint p) {
		TranslationCommand command = (TranslationCommand) p.getThis();
		log.info("Calling [TranslationService(search)] for " + Arrays.asList(command.getParams().getAttributes()));
	}
	
}
