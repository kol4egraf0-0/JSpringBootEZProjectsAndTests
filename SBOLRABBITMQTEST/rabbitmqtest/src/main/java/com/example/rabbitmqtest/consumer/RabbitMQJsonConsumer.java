package com.example.rabbitmqtest.consumer;

import com.example.rabbitmqtest.dto.User;
import com.example.rabbitmqtest.publisher.RabbitMQJsonProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQJsonConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonConsumer.class);

    @RabbitListener(queues = {"${rabbitmq.jsonqueue.name}"})
    public void consumeJsonMessage(User user) {
        LOGGER.info(String.format("Сообщение JSON получено -> %s", user.toString()));
    }
}
