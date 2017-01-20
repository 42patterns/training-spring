package com.example.dictionary.logging;

import com.example.dictionary.TranslationProcess;
import com.example.dictionary.commands.Command;
import com.example.dictionary.commands.CommandFactory;
import com.example.dictionary.commands.TranslationCommand;
import com.example.dictionary.translation.TranslationService;
import org.aspectj.lang.JoinPoint;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.mock.env.MockPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        initializers = AuditLoggingIntegrationTest.LocalFilePropertyApplicationContextInitializer.class
)
public class AuditLoggingIntegrationTest {

    @Autowired
    CommandFactory factory;

    @Autowired
    AuditLoggingAspect aspect;

    @Test
    public void should_ivoke_aspect() {
        Command command = factory.getCommand(TranslationProcess.fromCommandParameters("search book"));
        command.execute();

        Mockito.verify(aspect).logWebServiceCall(Mockito.any(JoinPoint.class));
    }

    public static class LocalFilePropertyApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            PropertiesPropertySource properties = new MockPropertySource().withProperty("urlStringTemplate",
                    LocalFilePropertyApplicationContextInitializer.class.getResource("/words/book.html").toExternalForm());
            applicationContext.getEnvironment().getPropertySources().replace(StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME, properties);

        }
    }

    @Configuration
    @Import({LocalValidatorFactoryBean.class, PropertySourcesPlaceholderConfigurer.class, CommandFactory.class, TranslationService.class, TranslationCommand.class, LoggerWrapper.class})
    @EnableAspectJAutoProxy
    public static class TestConfiguration {
        @Bean
        public AuditLoggingAspect aspect() {
            AuditLoggingAspect aspect = new AuditLoggingAspect();
            return Mockito.spy(aspect);
        }
    }
}