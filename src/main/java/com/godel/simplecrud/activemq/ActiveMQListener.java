package com.godel.simplecrud.activemq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ActiveMQListener {

    @JmsListener(destination = "queue")
    public void processMessages(ActiveMQMessage message) {
        log.warn("Received message to {}: {}", message.getTo(), message.getMessage());
    }
}
