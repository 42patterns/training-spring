package com.example.dictionary.logging;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.example.dictionary.CommandParameters;
import com.example.dictionary.model.TranslationProcess;

@Component
@Aspect
public class AuditLoggingAspect {

	private static Logger log = Logger.getLogger(AuditLoggingAspect.class);
	
	@Before("execution(* com.example.dictionary.TranslationService.getDictionaryWords(*)) && "
			+ "args(process,..)")
	public void logWebServiceCall(TranslationProcess process) {
		log.info("Calling [TranslationService(search)] for " + Arrays.asList(process.getParams().getAttributes()));
	}
	
}
