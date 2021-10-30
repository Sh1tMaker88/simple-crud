package com.godel.simplecrud.activemq;

import lombok.Data;

@Data
public class ActiveMQMessage {

    private String to;
    private String message;
}
