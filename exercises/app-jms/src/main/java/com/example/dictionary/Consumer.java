package com.example.dictionary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.jms.*;

@Component
public class Consumer {
    static final Logger LOG = LoggerFactory.getLogger(Consumer.class);
    private static final String QUEUE = "words-queue";

    private final ConnectionFactory cf;

    public Consumer(ConnectionFactory cf) {
        this.cf = cf;
    }

    public void consumeMessage() {

        try {
            Connection connection = cf.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(QUEUE);
            MessageConsumer consumer = session.createConsumer(queue);
            connection.start();

            for (;;) {
                Message receivedMessage = consumer.receiveNoWait();
                if (receivedMessage != null) {
                    LOG.info("JMS message received with {} \n{}", receivedMessage.getJMSMessageID(), ((ObjectMessage)receivedMessage).getObject());
                }
                Thread.sleep(1000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    @JmsListener(destination = QUEUE)
//    public void receiveMessage(final Message<String> message) throws JMSException {
//        LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
//        MessageHeaders headers =  message.getHeaders();
//        LOG.info("Application : headers received : {}", headers);
//
//        String response = message.getPayload();
//        LOG.info("Application : response received : {}",response);
//
//        LOG.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
//    }

}