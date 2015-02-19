package com.example.audit;

import com.example.dictionary.DictionaryWord;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Aspect
@Component
public class TranslationMonitor {

    private TransactionLogRepository repository;

    @Autowired
    public TranslationMonitor(TransactionLogRepository repository) {
        this.repository = repository;
    }


    @Around("execution(* com.example.App.getTranslations(..))")
    public Object logTranslationAction(ProceedingJoinPoint joinPoint) throws Throwable {
        String word = (String) joinPoint.getArgs()[0];

        List<DictionaryWord> translations = (List<DictionaryWord>) joinPoint.proceed();

        TranslationLog log = new TranslationLog();
        log.setPolishWord(word);
        log.setCnt(translations.size());
        log.setTimestamp(new Date());
        repository.save(log);

        return translations;
    }

}
