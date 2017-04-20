package com.example.dictionary.logging;

import com.example.dictionary.CommandParameters;
import com.example.dictionary.TranslationProcess;
import com.example.dictionary.commands.TranslationCommand;
import com.example.dictionary.config.GenericTestConfiguration;
import com.example.dictionary.translation.TranslationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Properties;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {AuditLoggingAspectTest.AspectConfiguration.class,
        TranslationCommand.class, TranslationService.class, AuditLoggingAspect.class, LocalValidatorFactoryBean.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class AuditLoggingAspectTest {

    @Autowired
    BeanFactory factory;

    @Autowired
    AuditLoggingAspect aspect;

    @Test
    public void should_aspect_call_the_logging_wrapper() {
        TranslationProcess process = TranslationProcess.fromCommandParameters(CommandParameters.from("search book"));
        TranslationCommand command = factory.getBean(TranslationCommand.class, process);

        command.execute();

        verify(aspect.log).info(isA(Class.class), contains("book"));
    }

    @Configuration
    @EnableAspectJAutoProxy
    public static class AspectConfiguration extends GenericTestConfiguration {

        @Bean
        public LoggerWrapper logger() {
            return mock(LoggerWrapper.class);
        }

        @Bean
        public PropertySourcesPlaceholderConfigurer serverConfiguration() {
            Properties props = new Properties();
            props.setProperty("urlStringTemplate", AspectConfiguration.class.getResource("/words/book.html").toExternalForm());

            PropertySourcesPlaceholderConfigurer p = new PropertySourcesPlaceholderConfigurer();
            p.setProperties(props);
            return p;
        }


    }

}

