package com.example.dictionary.logging;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class LoggerWrapper {

	private static Logger log = Logger.getLogger(Logger.class);
	
	public void info(Class<?> klazz, String msg) {
		log.info(klazz.getSimpleName() + ":: " + msg);
	}
	
}
