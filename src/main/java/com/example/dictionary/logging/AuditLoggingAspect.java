package com.example.dictionary.logging;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.example.dictionary.CommandParameters;

@Component
@Aspect
public class AuditLoggingAspect {

	private static Logger log = Logger.getLogger(AuditLoggingAspect.class);
	
	@Before("execution(* com.example.dictionary.TranslationService.getDictionaryWords(*)) && "
			+ "args(params,..)")
	public void logWebServiceCall(CommandParameters params) {
		log.info("Calling [TranslationService(search)] for " + Arrays.asList(params.getAttributes()));
	}
	
}
