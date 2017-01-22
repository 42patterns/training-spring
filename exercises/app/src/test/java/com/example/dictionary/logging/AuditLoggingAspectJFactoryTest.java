package com.example.dictionary.logging;

import com.example.dictionary.CommandParameters;
import com.example.dictionary.TranslationProcess;
import com.example.dictionary.commands.TranslationCommand;
import com.example.dictionary.translation.TranslationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuditLoggingAspectJFactoryTest {

    @Spy
    TranslationService service = new TranslationService(this.getClass().getResource("/words/book.html").toExternalForm());

    @InjectMocks
    TranslationCommand target = new TranslationCommand(TranslationProcess.fromCommandParameters("search book"));

    @Test
    public void should_invoke_aspect() {
        AspectJProxyFactory factory = new AspectJProxyFactory(target);

        AuditLoggingAspect aspect = new AuditLoggingAspect();
        aspect.log = mock(LoggerWrapper.class);
        factory.addAspect(aspect);

        TranslationCommand proxy = factory.getProxy();
        proxy.execute();

        verify(aspect.log).info(isA(Class.class), contains("book"));
    }

}
