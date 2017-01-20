package com.example.dictionary.logging;

import com.example.dictionary.TranslationProcess;
import com.example.dictionary.commands.TranslationCommand;
import com.example.dictionary.model.DictionaryWord;
import com.example.dictionary.translation.TranslationService;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.context.ApplicationContext;

import java.util.Dictionary;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@Ignore
public class AuditLoggingAspectJFactoryTest {

    @Mock
    TranslationService service;

    @Test
    public void should_invoke_aspect() {
        TranslationCommand target = new TranslationCommand(TranslationProcess.fromCommandParameters("search book"));

        AspectJProxyFactory factory = new AspectJProxyFactory(target);

        AuditLoggingAspect aspect = new AuditLoggingAspect();
        aspect.log = mock(LoggerWrapper.class);
        factory.addAspect(aspect);

        TranslationCommand proxy = factory.getProxy();
        proxy.execute();

        verify(aspect.log).info(isA(Class.class), anyString());
    }

}
