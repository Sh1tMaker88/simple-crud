package com.godel.simplecrud.activemq;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/active")
public class ActiveMQControllerRest {

    private final JmsTemplate jmsTemplate;

    public ActiveMQControllerRest(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @PostMapping(value = "/send")
    public String sendMessage(@RequestBody ActiveMQMessage message) {
        jmsTemplate.convertAndSend("queue", message);
        return "Done.";
    }
}
