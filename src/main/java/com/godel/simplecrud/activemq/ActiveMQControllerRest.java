package com.godel.simplecrud.activemq;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/active")
public class ActiveMQControllerRest {

    private final JmsTemplate jmsTemplate;

    public ActiveMQControllerRest(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @GetMapping(value = "/send/{message}", produces = "text/html")
    public String sendMessage(@PathVariable String message) {
        jmsTemplate.convertAndSend("queue", message);
        return "done";
    }
}
