package com.example.dictionary;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

import javax.jms.ConnectionFactory;

public class AsyncApp {

    public static void main(String[] args) {

        GenericApplicationContext context = new AnnotationConfigApplicationContext(
                MessagingListnerConfiguration.class);

        Consumer bean = context.getBean(Consumer.class);
        bean.consumeMessage();
    }

    @Configuration
    @EnableJms
    public static class MessagingListnerConfiguration {

        private static final String DEFAULT_BROKER_URL = "tcp://localhost:61616";

//        @Bean
//        public MessageReceiver receiver() {
//            return new MessageReceiver();
//        }

        @Bean
        public Consumer consumer(ConnectionFactory cf) {
            return new Consumer(cf);
        }

        @Bean
        public ActiveMQConnectionFactory connectionFactory(){
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
            connectionFactory.setBrokerURL(DEFAULT_BROKER_URL);
            connectionFactory.setTrustAllPackages(true);
            return connectionFactory;
        }

        @Bean
        public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(ConnectionFactory cf) {
            DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
            factory.setConnectionFactory(cf);
            factory.setConcurrency("1-1");
            return factory;
        }

    }
}
