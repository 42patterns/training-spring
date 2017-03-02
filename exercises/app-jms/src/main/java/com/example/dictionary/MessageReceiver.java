package com.example.dictionary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;

@Component
public class MessageReceiver {
    static final Logger LOG = LoggerFactory.getLogger(MessageReceiver.class);
    private static final String QUEUE = "words-queue";

    @JmsListener(destination = QUEUE)
    public void receiveMessage(final Message<String> message) throws JMSException {
        LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
        MessageHeaders headers =  message.getHeaders();
        LOG.info("Application : headers received : {}", headers);

        String response = message.getPayload();
        LOG.info("Application : response received : {}",response);

        LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }
}