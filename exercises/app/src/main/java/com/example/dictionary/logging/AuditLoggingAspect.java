package com.example.dictionary.logging;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.dictionary.commands.TranslationCommand;

@Component
@Aspect
public class AuditLoggingAspect {

	@Autowired
	LoggerWrapper log;
	
	@Before("execution(* com.example.dictionary.commands.TranslationCommand.execute())")
	public void logWebServiceCall(JoinPoint p) {
		TranslationCommand command = (TranslationCommand) p.getThis();
		log.info(this.getClass(), "Calling [TranslationService(search)] for " + Arrays.asList(command.getParams().getAttributes()));
	}
	
}
