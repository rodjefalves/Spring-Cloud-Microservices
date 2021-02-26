package br.com.conductor.pagamentoservice.rabbitmq;

import br.com.conductor.pagamentoservice.rest.dto.AtualizarEstoqueDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class OrderQueueSender implements Serializable {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Queue queue;

    public void send(AtualizarEstoqueDTO atualizarEstoqueDTO) {
        String serializedObj = "";
        try {
            serializedObj = objectMapper.writeValueAsString(atualizarEstoqueDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        //rabbitTemplate.convertAndSend("", QUEUE_NAME, serializedObj);
        rabbitTemplate.convertAndSend(this.queue.getName(), serializedObj);
    }
}